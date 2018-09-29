package com.vvizard.aenaflight.model;

import com.vvizard.aenaflight.model.Agregator;
import com.vvizard.aenaflight.model.AfSource;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;


import java.util.HashMap;
import java.util.concurrent.atomic.LongAccumulator;

@Component
@Scope("singleton")
public class Dispetcher {


    public final LongAccumulator saveRecords=new LongAccumulator((a, b) -> a + b, 0);

    public final HashMap<String, Agregator> agregators= new HashMap<>();

    public void putToAgregators(Agregator afs){
        agregators.put(afs.getKey(), afs);
    }

    public Agregator getAfsoutceAgregator(AfSource afs){
        return agregators.get(afs.getKey());
    }

    public Boolean constaint(String key){
        return agregators.containsKey(key);
    }

}
