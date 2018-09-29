package com.vvizard.aenaflight.service;


import com.vvizard.aenaflight.model.AfSource;
import com.vvizard.aenaflight.model.Agregator;
import com.vvizard.aenaflight.model.Dispetcher;
import com.vvizard.aenaflight.repository.DestinationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import java.math.BigInteger;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Queue;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class AgregatorService {

    @NotNull
    private final DestinationRepository destinationRepository;

    @NotNull
    private final DispetcherService dispetcherService;

    @NotNull
    private final Dispetcher dispetcher;

    public void addAfSource(Agregator agregator, AfSource afSource){
        agregator.getAfSourceQueue().add(afSource);
    }

    public Runnable getWorker(Agregator agregator) {
        return () -> {
            LocalDate agregatorDate, sourceDate;
            try {
                agregator.getSemaphore().acquire();
                Queue<AfSource> queue=agregator.getAfSourceQueue();
                AfSource afs;
                while (queue.size()>0){
                    if ((afs=queue.poll())!=null){
                        String schd_dep_only_date_lt=agregator.getBaseSource().getSchd_dep_only_date_lt();
                        String afsSchd_dep_only_date_lt=afs.getSchd_dep_only_date_lt();
                        join(agregator, afs);
                    }
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                agregator.getSemaphore().release();
            }

        };

    }

    private void join(Agregator agregator, AfSource afs) {
        AfSource baseSource=agregator.getBaseSource();
        agregator.getIdCollection().add(afs.getId());
        if (baseSource.getCreated_at().compareTo(afs.getCreated_at())<0){

            baseSource.setCreated_at(afs.getCreated_at());

            if (!StringUtils.isEmpty(afs.getAct_arr_date_time_lt())){
                baseSource.setAct_arr_date_time_lt(afs.getAct_arr_date_time_lt());
            }

            if (!StringUtils.isEmpty(afs.getArr_apt_code_iata())){
                baseSource.setArr_apt_code_iata(afs.getArr_apt_code_iata());
            }

            if (!StringUtils.isEmpty(afs.getAircraft_name_scheduled())){
                baseSource.setAircraft_name_scheduled(afs.getAircraft_name_scheduled());
            }


            if (!StringUtils.isEmpty(afs.getCarrier_icao_code())){
                baseSource.setCarrier_icao_code(afs.getCarrier_icao_code());
            }

            if (!StringUtils.isEmpty(afs.getCarrier_number())){
                baseSource.setCarrier_number(afs.getCarrier_number());
            }


            if (!StringUtils.isEmpty(afs.getDep_apt_code_iata())){
                baseSource.setDep_apt_code_iata(afs.getDep_apt_code_iata());
            }

            if (!StringUtils.isEmpty(afs.getEst_arr_date_time_lt())){
                baseSource.setEst_arr_date_time_lt(afs.getEst_arr_date_time_lt());
            }

            if (!StringUtils.isEmpty(afs.getEst_dep_date_time_lt())){
                baseSource.setEst_dep_date_time_lt(afs.getEst_dep_date_time_lt());
            }


            if (!StringUtils.isEmpty(afs.getFlt_leg_seq_no())){
                baseSource.setFlt_leg_seq_no(afs.getFlt_leg_seq_no());
            }

            if (!StringUtils.isEmpty(afs.getSchd_arr_only_date_lt())){
                baseSource.setSchd_arr_only_date_lt(afs.getSchd_arr_only_date_lt());
            }

            if (!StringUtils.isEmpty(afs.getSchd_arr_only_time_lt())){
                baseSource.setSchd_arr_only_time_lt(afs.getSchd_arr_only_time_lt());
            }

            if (!StringUtils.isEmpty(afs.getSource_data())){
                baseSource.setSource_data(afs.getSource_data());
            }

            if (!StringUtils.isEmpty(afs.getStatus_info())){
                baseSource.setStatus_info(afs.getStatus_info());
            }

            if (!StringUtils.isEmpty(afs.getAct_dep_date_time_lt())){
                baseSource.setAct_dep_date_time_lt(afs.getAct_dep_date_time_lt());
            }

            if (!StringUtils.isEmpty(afs.getSchd_dep_only_time_lt())){
                baseSource.setSchd_dep_only_time_lt(afs.getSchd_dep_only_time_lt());
            }


            ArrayList<String> counters=agregator.getCounters();
            if (!StringUtils.isEmpty(afs.getCounter())
                    && (!counters.get(counters.size()-1).equals(afs.getCounter()))){
                counters.add(afs.getCounter());
            }

            ArrayList<String> baggages_info=agregator.getBaggages_info();
            if (!StringUtils.isEmpty(afs.getBaggage_info())
                    && (!baggages_info.get(baggages_info.size()-1).equals(afs.getBaggage_info()))){
                baggages_info.add(afs.getBaggage_info());
            }

            ArrayList<String> gates_info=agregator.getGates_info();
            if (!StringUtils.isEmpty(afs.getGate_info())
                    && (!gates_info.get(gates_info.size()-1).equals(afs.getGate_info()))){
                gates_info.add(afs.getGate_info());
            }

            ArrayList<String> lounges_info=agregator.getLounges_info();
            if (!StringUtils.isEmpty(afs.getLounge_info())
                    && (!lounges_info.get(lounges_info.size()-1).equals(afs.getLounge_info()))){
                lounges_info.add(afs.getLounge_info());
            }

            ArrayList<String> terminals_info=agregator.getTerminals_info();
            if (!StringUtils.isEmpty(afs.getTerminal_info())
                    && (!terminals_info.get(terminals_info.size()-1).equals(afs.getTerminal_info()))){
                terminals_info.add(afs.getTerminal_info());
            }

            ArrayList<String> arr_terminals_info=agregator.getArr_terminals_info();
            if (!StringUtils.isEmpty(afs.getArr_terminal_info())
                    && (!arr_terminals_info.get(arr_terminals_info.size()-1).equals(afs.getArr_terminal_info()))){
                arr_terminals_info.add(afs.getArr_terminal_info());
            }

            agregator.setMaxId(afs.getId().longValue());

        }


    }


    public Boolean isCorrect(Agregator agregator){
        AfSource afSource=agregator.getBaseSource();

        if (StringUtils.isEmpty(afSource.getDep_apt_code_iata())) return false;

        if (StringUtils.isEmpty(afSource.getArr_apt_code_iata())) return false;

        if (StringUtils.isEmpty(afSource.getFlight_icao_code())) return false;

        if (StringUtils.isEmpty(afSource.getFlight_number())) return false;

        if (StringUtils.isEmpty(afSource.getStatus_info())) return false;

        if (StringUtils.isEmpty(afSource.getSchd_dep_only_date_lt())) return false;

        if (StringUtils.isEmpty(afSource.getSchd_dep_only_time_lt())) return false;

        if (StringUtils.isEmpty(afSource.getSchd_arr_only_date_lt())) return false;

        if (StringUtils.isEmpty(afSource.getSchd_arr_only_time_lt())) return false;

        if (StringUtils.isEmpty(afSource.getFlt_leg_seq_no())) return false;

        return (afSource.getCreated_at() != null) && (afSource.getCreated_at().compareTo(BigInteger.ZERO) != 0);
    }

}

