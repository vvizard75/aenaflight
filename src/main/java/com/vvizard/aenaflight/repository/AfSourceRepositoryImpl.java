package com.vvizard.aenaflight.repository;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Tuple;
import java.util.stream.Stream;

@Repository
public class AfSourceRepositoryImpl {

    @PersistenceContext
    private EntityManager em;

    private static final int FSIZE = 500;


    @Value("${aenaflight.tablename}")
    private String table;

    public Stream<Tuple> streamTuple() {
        Session session = em.unwrap(Session.class);
        return session.createNativeQuery("select * from "+table+
                " where (schd_dep_only_date_lt='') is not true " +
                " order by schd_dep_only_date_lt, id", Tuple.class)
                .setHint("org.hibernate.fetchSize", FSIZE)
                .setHint("org.hibernate.readOnly", true).stream();
    }

}
