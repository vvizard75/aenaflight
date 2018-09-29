package com.vvizard.aenaflight.service;

import com.vvizard.aenaflight.model.*;
import com.vvizard.aenaflight.repository.AfSourceRepositoryImpl;
import com.vvizard.aenaflight.repository.DestinationRepository;
import com.vvizard.aenaflight.service.AgregatorService;
import com.vvizard.aenaflight.service.DestinationService;
import com.vvizard.aenaflight.service.DispetcherService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Set;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class AfSourceService {


    @NotNull
    private final AfSourceRepositoryImpl afSourceRepository;

    @NotNull
    private final AgregatorService agregatorService;

    @NotNull
    private final Dispetcher dispetcher;

    @NotNull
    private final DispetcherService dispetcherService;

    @NotNull
    private final DestinationService destinationService;

    private Boolean end=false;

    @NotNull
    private final DestinationRepository destinationRepository;


    private Semaphore dispetcherSemaphor;

    enum Status{
        START,
        WORK,
        FINISH

    }
    private Status status=Status.START;
    @Transactional()
    public void runAfSourceStream() {




        Set<String>loadedKeySet=destinationService.getKeyLoadedRecords();

        int psize = Runtime.getRuntime().availableProcessors() + 1;
        dispetcherSemaphor =new Semaphore(1);

        System.out.printf("Thread count =%d\n", psize);

        BlockingQueue<Runnable> queue = new LinkedTransferQueue<Runnable>() {
            @Override
            public boolean offer(Runnable e) {
                return tryTransfer(e);
            }
        };

        AtomicInteger dr = new AtomicInteger(0);
        ThreadPoolExecutor executor = new ThreadPoolExecutor(1, psize, 60, TimeUnit.SECONDS, queue);
        executor.setRejectedExecutionHandler((r, executor1) -> {
            try {
                executor1.getQueue().put(r);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        Runnable runInformator = () -> {
            try {
                char [] chars={'|', '\\', '-', '/'};
                while (!(status==Status.FINISH)) {
                    while (status==Status.WORK){
                        System.out.printf("\rRead records: %d; Saved records: %d; In memory records: %d",
                                dr.get(), dispetcher.saveRecords.longValue(), dispetcher.agregators.size());
                        TimeUnit.SECONDS.sleep(5);
                    }
                    while (status==Status.START){
                        for (int i = 0; i < chars.length; i++) {
                            System.out.printf("\r%s", chars[i]);
                            TimeUnit.MILLISECONDS.sleep(500);
                        }
                    }

                    TimeUnit.MILLISECONDS.sleep(500);
                }
                System.out.printf("\nFinish!\nFor exit press Ctr+C\n");

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };

        Thread threadInformator = new Thread(runInformator);
        threadInformator.start();


        try (Stream<Tuple> afsStream = afSourceRepository.streamTuple()) {

            afsStream.filter(v->{
                String keyTuple=v.get("flight_icao_code", String.class)+v.get("flight_number", String.class)+
                        v.get("schd_dep_only_date_lt", String.class);
                return !loadedKeySet.contains(keyTuple);
            }).forEach((afsTuple) -> {
                if (status!=Status.WORK) status=Status.WORK;
                dr.incrementAndGet();
                AfSource af = new AfSource(afsTuple);
                if (!dispetcherService.equalDate(af.getSchdDepDate())) {
                    while (executor.getQueue().size() > 0) {
                        try {
                            TimeUnit.MILLISECONDS.sleep(100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    clearAgregators();
                }

                if (dispetcher.constaint(af.getKey())) {
                    Agregator agregator = dispetcher.getAfsoutceAgregator(af);
                    agregatorService.addAfSource(agregator, af);
                    executor.submit(agregatorService.getWorker(agregator));
                } else {
                    dispetcher.putToAgregators(new Agregator(af));
                }

            });

        }

        clearAgregators();
        try {
            TimeUnit.MILLISECONDS.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        status=Status.FINISH;
    }


    private void clearAgregators() {
        HashMap<String, Agregator> agregators =
                new HashMap<>(dispetcher.agregators);
        dispetcher.agregators.clear();
        Runnable runToDesationation = ()  -> {
            try {
                dispetcherSemaphor.acquire();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            final LocalDate[] controlDate = {null};

            agregators.forEach((k, v) -> {
                if (agregatorService.isCorrect(v)) {

                    Destination destination = new Destination(v);
                    if (controlDate[0] ==null){
                        controlDate[0] =destination.getSchd_dep_lt().toLocalDate().plusDays(1);
                    }

                      destinationRepository.save(destination);

                }

            });
            dispetcherSemaphor.release();
        };

        Thread threadToDesationation = new Thread(runToDesationation);
        threadToDesationation.start();


    }

}

