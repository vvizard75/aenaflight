package com.vvizard.aenaflight.model;


import lombok.*;
import org.apache.commons.lang3.StringUtils;


import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;

@Table(name = "aenaflight_source")
@Setter
@Getter
@NoArgsConstructor
@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Destination {

    private static final DateTimeFormatter dateTimeFormatter=DateTimeFormatter.ofPattern("dd/MM/yy HH:mm");

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "bigserial")
    private Long id;

    @Column(nullable = false, length = 8)
    @NotNull
    @NotBlank
    @Setter
    @EqualsAndHashCode.Include
    private String adep;

    @NotNull
    @NotBlank
    @Column(nullable = false, length = 8)
    @EqualsAndHashCode.Include
    private String ades;

    @NotNull
    @NotBlank
    @Column(nullable = false, length = 8)
    @EqualsAndHashCode.Include
    private String flight_code;

    @NotNull
    @NotBlank
    @Column(nullable = false, length = 8)
    @EqualsAndHashCode.Include
    private String flight_number;

    @Column(length = 8)
    @EqualsAndHashCode.Include
    private String carrier_code;

    @Column(length = 8)
    @EqualsAndHashCode.Include
    private String carrier_number;

    @NotNull
    @NotBlank
    @Column(nullable = false, length = 256)
    @EqualsAndHashCode.Include
    private String status_info;

    @NotNull
    @Column(nullable = false)
    @EqualsAndHashCode.Include
    private LocalDateTime schd_dep_lt;

    @NotNull
    @Column(nullable = false)
    @EqualsAndHashCode.Include
    private LocalDateTime schd_arr_lt;

    @EqualsAndHashCode.Include
    private LocalDateTime est_dep_lt;

    @EqualsAndHashCode.Include
    private LocalDateTime est_arr_lt;

    @EqualsAndHashCode.Include
    private LocalDateTime act_dep_lt;

    @EqualsAndHashCode.Include
    private LocalDateTime act_arr_lt;

    @NotNull
    @Column(nullable = false)
    @EqualsAndHashCode.Include
    private Integer flt_leg_seq_no;

    @Column(columnDefinition = "text")
    @EqualsAndHashCode.Include
    private String aircraft_name_scheduled;

    @Column(length = 128)
    @EqualsAndHashCode.Include
    private String baggage_info;

    @Column(length = 128)
    @EqualsAndHashCode.Include
    private String counter;

    @Column(length = 128)
    @EqualsAndHashCode.Include
    private String gate_info;

    @Column(length = 128)
    @EqualsAndHashCode.Include
    private String lounge_info;

    @Column(length = 128)
    @EqualsAndHashCode.Include
    private String terminal_info;

    @Column(length = 128)
    @EqualsAndHashCode.Include
    private String arr_terminal_info;

    @Column(columnDefinition = "text")
    @EqualsAndHashCode.Include
    private String source_data;

    @NotNull
    @Column(nullable = false)
    @EqualsAndHashCode.Include
    private LocalDateTime created_at;


    public Destination(Agregator agregator){


        AfSource afSource=agregator.getBaseSource();

        setCreated_at(LocalDateTime.ofEpochSecond(afSource.getCreated_at().longValue(), 0, ZoneOffset.UTC));

        setAdep(afSource.getDep_apt_code_iata());

        setAdes(afSource.getArr_apt_code_iata());

        setFlight_code(afSource.getFlight_icao_code());

        setFlight_number(afSource.getFlight_number());

        setCarrier_code(afSource.getCarrier_icao_code());

        setCarrier_number(afSource.getCarrier_number());

        setStatus_info(afSource.getStatus_info());

        setSchd_dep_lt(LocalDateTime.parse(afSource.getSchd_dep_only_date_lt()
                +" "+afSource.getSchd_dep_only_time_lt(), dateTimeFormatter));

        setSchd_arr_lt(LocalDateTime.parse(afSource.getSchd_arr_only_date_lt()
                +" "+afSource.getSchd_arr_only_time_lt(), dateTimeFormatter));

        if (!StringUtils.isEmpty(afSource.getEst_dep_date_time_lt())){
            setEst_dep_lt(LocalDateTime.parse(afSource.getEst_dep_date_time_lt(), dateTimeFormatter));
        }

        if (!StringUtils.isEmpty(afSource.getEst_arr_date_time_lt())){
            setEst_arr_lt(LocalDateTime.parse(afSource.getEst_arr_date_time_lt(), dateTimeFormatter));
        }

        if (!StringUtils.isEmpty(afSource.getAct_dep_date_time_lt())){
            setAct_dep_lt(LocalDateTime.parse(afSource.getAct_dep_date_time_lt(), dateTimeFormatter));
        }

        if (!StringUtils.isEmpty(afSource.getAct_arr_date_time_lt())){
            setAct_arr_lt(LocalDateTime.parse(afSource.getAct_arr_date_time_lt(), dateTimeFormatter));
        }

        setFlt_leg_seq_no(Integer.getInteger(afSource.getFlt_leg_seq_no(), 0));

        setAircraft_name_scheduled(afSource.getAircraft_name_scheduled());

        setSource_data(afSource.getSource_data());

        setBaggage_info(arrToStr(agregator.getBaggages_info()));

        setCounter(arrToStr(agregator.getCounters()));

        setGate_info(arrToStr(agregator.getGates_info()));

        setLounge_info(arrToStr(agregator.getLounges_info()));

        setTerminal_info(arrToStr(agregator.getTerminals_info()));

        setArr_terminal_info(arrToStr(agregator.getArr_terminals_info()));
    }

    private String arrToStr(ArrayList<String> arr){
        Collections.reverse(arr);

        return String.join(",", arr);
    }


    public void setAdep(String adep) {
        this.adep = StringUtils.left(adep, 8);
    }

    public void setAdes(String ades) {
        this.ades = StringUtils.left(ades, 8);
    }

    public void setFlight_code(String flight_code) {
        this.flight_code = StringUtils.left(flight_code, 8);
    }

    public void setFlight_number(String flight_number) {
        this.flight_number = StringUtils.left(flight_number, 8);
    }

    public void setCarrier_code(String carrier_code) {
        this.carrier_code = StringUtils.left(carrier_code, 8);
    }

    public void setCarrier_number(String carrier_number) {
        this.carrier_number = StringUtils.left(carrier_number, 8);
    }

    public void setStatus_info(String status_info) {
        this.status_info = StringUtils.left(status_info, 256);
    }

    public void setBaggage_info(String baggage_info) {
        this.baggage_info = StringUtils.left(baggage_info, 128);
    }

    public void setCounter(String counter) {
        this.counter = StringUtils.left(counter, 128);
    }

    public void setGate_info(String gate_info) {
        this.gate_info = StringUtils.left(gate_info, 128);
    }

    public void setLounge_info(String lounge_info) {
        this.lounge_info = StringUtils.left(lounge_info, 128);
    }

    public void setTerminal_info(String terminal_info) {
        this.terminal_info = StringUtils.left(terminal_info, 128);
    }

    public void setArr_terminal_info(String arr_terminal_info) {
        this.arr_terminal_info = StringUtils.left(arr_terminal_info, 128);
    }

    @Override
    public String toString() {
        return "Destination{" +
                "id=" + id +
                ", adep='" + adep + '\'' +
                ", ades='" + ades + '\'' +
                ", flight_code='" + flight_code + '\'' +
                ", flight_number='" + flight_number + '\'' +
                ", carrier_code='" + carrier_code + '\'' +
                ", carrier_number='" + carrier_number + '\'' +
                ", status_info='" + status_info + '\'' +
                ", schd_dep_lt=" + schd_dep_lt +
                ", schd_arr_lt=" + schd_arr_lt +
                ", est_dep_lt=" + est_dep_lt +
                ", est_arr_lt=" + est_arr_lt +
                ", act_dep_lt=" + act_dep_lt +
                ", act_arr_lt=" + act_arr_lt +
                ", flt_leg_seq_no=" + flt_leg_seq_no +
                ", aircraft_name_scheduled='" + aircraft_name_scheduled + '\'' +
                ", baggage_info='" + baggage_info + '\'' +
                ", counter='" + counter + '\'' +
                ", gate_info='" + gate_info + '\'' +
                ", lounge_info='" + lounge_info + '\'' +
                ", terminal_info='" + terminal_info + '\'' +
                ", arr_terminal_info='" + arr_terminal_info + '\'' +
                ", source_data='" + source_data + '\'' +
                ", created_at=" + created_at +
                '}';
    }
}
