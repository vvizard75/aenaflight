package com.vvizard.aenaflight.model;

import lombok.*;



import javax.persistence.*;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Getter
@Setter
public class AfSource{

    private static final DateTimeFormatter DATE_TIME_FORMATTER=DateTimeFormatter.ofPattern("dd/MM/yy");
    private BigInteger id;


    private String act_arr_date_time_lt;

    private String aircraft_name_scheduled;

    private String arr_apt_code_iata;

    private String baggage_info;

    private String carrier_icao_code;

    private String carrier_number;

    private String counter;

    private String dep_apt_code_iata;

    private String est_arr_date_time_lt;

    private String est_dep_date_time_lt;

    @EqualsAndHashCode.Include
    private String flight_icao_code;

    @EqualsAndHashCode.Include
    private String flight_number;

    private String flt_leg_seq_no;

    private String gate_info;

    private String lounge_info;

    private String schd_arr_only_date_lt;

    private String schd_arr_only_time_lt;

    private String source_data;

    private String status_info;

    private String terminal_info;

    private String arr_terminal_info;

    private String act_dep_date_time_lt;

    private String schd_dep_only_date_lt;

    private String schd_dep_only_time_lt;

    private BigInteger created_at;

    public AfSource(Tuple tuple){

        setId(tuple.get("id", BigInteger.class));

        setAct_arr_date_time_lt(tuple.get("act_arr_date_time_lt", String.class));

        setAircraft_name_scheduled((String) tuple.get("aircraft_name_scheduled"));

        setArr_apt_code_iata(tuple.get("arr_apt_code_iata", String.class));

        String baggage_info=tuple.get("baggage_info", String.class);
        setBaggage_info(baggage_info.equals("-") ? "" : baggage_info);

        setCarrier_icao_code(tuple.get("carrier_icao_code", String.class));

        setCarrier_number(tuple.get("carrier_number", String.class));

        String counter=tuple.get("counter", String.class);
        setCounter(counter.equals("-") ? "" : counter);

        setDep_apt_code_iata(tuple.get("dep_apt_code_iata", String.class));

        setEst_arr_date_time_lt(tuple.get("est_arr_date_time_lt", String.class));

        setEst_dep_date_time_lt(tuple.get("est_dep_date_time_lt", String.class));

        setFlight_icao_code(tuple.get("flight_icao_code", String.class));

        setFlight_number(tuple.get("flight_number", String.class));

        setFlt_leg_seq_no(tuple.get("flt_leg_seq_no", String.class));

        String gate_info=tuple.get("gate_info", String.class);
        setGate_info(gate_info.equals("-") ? "" : gate_info);

        String lounge_info=tuple.get("lounge_info", String.class);
        setLounge_info(lounge_info.equals("-") ? "" : lounge_info);

        setSchd_arr_only_date_lt(tuple.get("schd_arr_only_date_lt", String.class));

        setSchd_arr_only_time_lt(tuple.get("schd_arr_only_time_lt", String.class));

        setSource_data(tuple.get("source_data", String.class));

        setStatus_info(tuple.get("status_info", String.class));

        String terminal_info=tuple.get("terminal_info", String.class);
        setTerminal_info(terminal_info.equals("-") ? "" : terminal_info);

        String arr_terminal_info=tuple.get("arr_terminal_info", String.class);
        setArr_terminal_info(arr_terminal_info.equals("-") ? "" : arr_terminal_info);

        setAct_dep_date_time_lt(tuple.get("act_dep_date_time_lt", String.class));

        setSchd_dep_only_date_lt(tuple.get("schd_dep_only_date_lt", String.class));

        setSchd_dep_only_time_lt(tuple.get("schd_dep_only_time_lt", String.class));

        setCreated_at(tuple.get("created_at", BigInteger.class));

    }

    public LocalDate getSchdDepDate(){
        return LocalDate.parse(getSchd_dep_only_date_lt(), DATE_TIME_FORMATTER);
    }

    public String getKey(){
        return this.getFlight_icao_code()+this.getFlight_number();
    }
}
