package com.vvizard.aenaflight;

import com.vvizard.aenaflight.model.Destination;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AenaflightApplicationTests {


    @PersistenceContext
    private EntityManager em;


    @Test
    public void contextLoads() {

    }

    @Test
    public void testCount(){
        Query query=em.createQuery("from Destination");
        List<Destination> list=query.getResultList();
        assertEquals(2, list.size());
    }

    @Test
    public void testContentDestination(){
        Query query=em.createQuery("from Destination");
        List<Destination> list=query.getResultList();
        Destination[] dd=getEtalonDestiantions();
        assertFalse(!(list.contains(dd[0])&&list.contains(dd[1])));
    }


    private Destination[] getEtalonDestiantions(){
        Destination d= new Destination();
        d.setAdes("ACE");
        d.setAdep("LPA");
        d.setFlight_code("IBB");
        d.setFlight_number("526");
        d.setStatus_info("The flight landed");
        d.setSchd_dep_lt(LocalDateTime.parse("2017-01-01T18:00:00"));
        d.setSchd_arr_lt(LocalDateTime.parse("2017-01-01T18:45:00"));
        d.setEst_dep_lt(LocalDateTime.parse("2017-01-01T18:00:00"));
        d.setEst_arr_lt(LocalDateTime.parse("2017-01-01T18:44:00"));
        d.setAct_dep_lt(LocalDateTime.parse("2017-01-01T17:58:00"));
        d.setAct_arr_lt(LocalDateTime.parse("2017-01-01T18:45:00"));
        d.setFlt_leg_seq_no(0);
        d.setAircraft_name_scheduled("ATR-72");
        d.setBaggage_info("C2");
        d.setCounter("103 - 108");
        d.setGate_info("A13");
        d.setLounge_info("4");
        d.setTerminal_info("");
        d.setArr_terminal_info("4");
        d.setSource_data("http://www.aena.es/csee/Satellite/infovuelos/en/?nvuelo=526&company=IBB&hprevista=2017-01-01+18:45");
        d.setCreated_at(LocalDateTime.parse("2017-01-01T20:41:55"));

        Destination d1=new Destination();
        d1.setAdes("BCN");
        d1.setAdep("VLL");
        d1.setFlight_code("IBE");
        d1.setFlight_number("5948");
        d1.setCarrier_code("VLG");
        d1.setCarrier_number("1585");
        d1.setStatus_info("The flight landed");
        d1.setSchd_dep_lt(LocalDateTime.parse("2017-01-06T09:55:00"));
        d1.setSchd_arr_lt(LocalDateTime.parse("2017-01-06T11:10:00"));
        d1.setEst_dep_lt(LocalDateTime.parse("2017-01-06T09:55:00"));
        d1.setEst_arr_lt(LocalDateTime.parse("2017-01-06T10:54:00"));
        d1.setAct_dep_lt(LocalDateTime.parse("2017-01-06T09:47:00"));
        d1.setAct_arr_lt(LocalDateTime.parse("2017-01-06T10:59:00"));
        d1.setFlt_leg_seq_no(0);
        d1.setAircraft_name_scheduled("32A");
        d1.setBaggage_info("");
        d1.setCounter("1 - 2");
        d1.setGate_info("1");
        d1.setLounge_info("");
        d1.setTerminal_info("1");
        d1.setArr_terminal_info("");
        d1.setSource_data("http://www.aena.es/csee/Satellite/infovuelos/en/?nvuelo=5948&company=IBE&hprevista=2017-01-06+11:10");
        d1.setCreated_at(LocalDateTime.parse("2017-01-06T11:45:09"));
        Destination[] dd={d, d1};
        return dd;
    }
}
