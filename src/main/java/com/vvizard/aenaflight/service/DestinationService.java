package com.vvizard.aenaflight.service;

import com.vvizard.aenaflight.repository.DestinationRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Tuple;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class DestinationService {
    private static final int FSIZE = 500;

    @PersistenceContext
    private
    EntityManager em;


    @NotNull
    private final DestinationRepository destinationRepository;

    public Set<String> getKeyLoadedRecords(){
        Set<String> setKey=new HashSet<>();
        Session session = em.unwrap(Session.class);
        try(Stream<Tuple> tupleStream=session.createNativeQuery("select flight_code, flight_number, schd_dep_lt from aenaflight_source", Tuple.class)
                .setHint("org.hibernate.fetchSize", FSIZE)
                .setHint("org.hibernate.readOnly", true).stream()){
            tupleStream.forEach((keyTuple)->{
                String date = new SimpleDateFormat("dd/MM/yy").format(keyTuple.get("schd_dep_lt", Timestamp.class));
                String key=keyTuple.get("flight_code", String.class)+
                        keyTuple.get("flight_number", String.class)+date;
                setKey.add(key);
            });
        }

        return setKey;
    }
}
