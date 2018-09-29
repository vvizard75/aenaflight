package com.vvizard.aenaflight.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import org.springframework.util.StringUtils;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Semaphore;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Agregator {

    @EqualsAndHashCode.Include
    @Getter
    private
    AfSource baseSource;

    @Getter
    private Long maxId=-1L;

    @Getter
    private final Set<BigInteger> idCollection=new HashSet<>();

    @Getter
    private Queue<AfSource> afSourceQueue= new ConcurrentLinkedQueue<>();

    @Getter
    private final Semaphore semaphore;

    @Getter
    private final ArrayList<String> baggages_info= new ArrayList<>();

    @Getter
    private final ArrayList<String> counters= new ArrayList<>();

    @Getter
    private final ArrayList<String> gates_info= new ArrayList<>();

    @Getter
    private final ArrayList<String> lounges_info= new ArrayList<>();

    @Getter
    private final ArrayList<String> terminals_info= new ArrayList<>();

    @Getter
    private final ArrayList<String> arr_terminals_info= new ArrayList<>();

    public Agregator(AfSource afSource){
        fill(afSource);

        semaphore=new Semaphore(1);
    }

    private void fill(AfSource afSource) {
        baseSource=afSource;
        idCollection.add(afSource.getId());
        counters.clear();
        baggages_info.clear();
        gates_info.clear();
        lounges_info.clear();
        terminals_info.clear();
        arr_terminals_info.clear();

        if (!StringUtils.isEmpty(baseSource.getCounter())){
            counters.add(baseSource.getCounter());
        }
        if (!StringUtils.isEmpty(baseSource.getBaggage_info())){
            baggages_info.add(baseSource.getBaggage_info());
        }
        if (!StringUtils.isEmpty(baseSource.getGate_info())){
            gates_info.add(baseSource.getGate_info());
        }
        if (!StringUtils.isEmpty(baseSource.getLounge_info())){
            lounges_info.add(baseSource.getLounge_info());
        }
        if (!StringUtils.isEmpty(baseSource.getTerminal_info())){
            terminals_info.add(baseSource.getTerminal_info());
        }
        if (!StringUtils.isEmpty(baseSource.getArr_terminal_info())){
            arr_terminals_info.add(baseSource.getArr_terminal_info());
        }
        setMaxId(afSource.getId().longValue());

    }



    public void setMaxId(Long maxId) {
        if (maxId>this.maxId){
            this.maxId = maxId;
        }

    }


    public String getKey() {
        return baseSource.getFlight_icao_code()+baseSource.getFlight_number();
    }
}
