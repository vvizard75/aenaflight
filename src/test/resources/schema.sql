drop table if exists aenaflight_2017_01;
create table if not exists aenaflight_2017_01
(
  id                      bigserial not null
    constraint pk_aenaflight_2017_01
    primary key,
  act_arr_date_time_lt    varchar(64),
  aircraft_name_scheduled varchar(256),
  arr_apt_name_es         varchar(128),
  arr_apt_code_iata       varchar(8),
  baggage_info            varchar(128),
  carrier_airline_name_en varchar(128),
  carrier_icao_code       varchar(8),
  carrier_number          varchar(8),
  counter                 varchar(64),
  dep_apt_name_es         varchar(128),
  dep_apt_code_iata       varchar(8),
  est_arr_date_time_lt    varchar(64),
  est_dep_date_time_lt    varchar(64),
  flight_airline_name_en  varchar(128),
  flight_airline_name     varchar(128),
  flight_icao_code        varchar(8),
  flight_number           varchar(8),
  flt_leg_seq_no          varchar(8),
  gate_info               varchar(128),
  lounge_info             varchar(128),
  schd_arr_only_date_lt   varchar(32),
  schd_arr_only_time_lt   varchar(32),
  source_data             varchar(256),
  status_info             varchar(128),
  terminal_info           varchar(128),
  arr_terminal_info       varchar(128),
  created_at              bigint,
  act_dep_date_time_lt    varchar(64),
  schd_dep_only_date_lt   varchar(32),
  schd_dep_only_time_lt   varchar(32)
);

drop table if exists aenaflight_source;
create table if not exists aenaflight_source
(
    id bigserial NOT NULL PRIMARY KEY,
    adep character varying(8) NOT NULL,
    ades character varying(8) NOT NULL,
    flight_code character varying(8) NOT NULL,
    flight_number character varying(8) NOT NULL,
    carrier_code character varying(8),
    carrier_number character varying(8),
    status_info character varying(256) NOT NULL,
    schd_dep_lt timestamp without time zone NOT NULL,
    schd_arr_lt timestamp without time zone NOT NULL,
    est_dep_lt timestamp without time zone,
    est_arr_lt timestamp without time zone,
    act_dep_lt timestamp without time zone,
    act_arr_lt timestamp without time zone,
    flt_leg_seq_no integer NOT NULL,
    aircraft_name_scheduled text,
    baggage_info character varying(128),
    counter character varying(128),
    gate_info character varying(128),
    lounge_info character varying(128),
    terminal_info character varying(128),
    arr_terminal_info character varying(128),
    source_data text,
    created_at timestamp without time zone NOT NULL
);
