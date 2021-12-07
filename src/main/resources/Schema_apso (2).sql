--1.2.1 Таблица LSFC_TriggerEvent
create table if not exists lsfc_trigger_event
(
    id                 bigint not null,
    timer_id           bigint,
    version_context_id bigint,
    changed_data       varchar(255),
    constraint lsfc_trigger_event_pk primary key (id)
);

create sequence seq_lsfc_trigger_event;

create index i1_lsfc_trigger_event on lsfc_trigger_event (timer_id);

create index i2_lsfc_trigger_event on lsfc_trigger_event (version_context_id);

--1.2.2 Таблица LSFC_ExecutionChain
create table if not exists lsfc_execution_chain
(
    id              bigint not null,
    kind_measure_id bigint,
    measure_id      bigint,
    context_id      bigint,
    constraint lsfc_execution_chain_pk primary key (id)
);

create sequence seq_lsfc_execution_chain;

create index i1_lsfc_execution_chain on lsfc_execution_chain (kind_measure_id);

create index i2_lsfc_execution_chain on lsfc_execution_chain (measure_id);

create index i3_lsfc_execution_chain on lsfc_execution_chain (context_id);

--1.2.3 Таблица LSFC_Execution
create table if not exists lsfc_execution
(
    id                 bigint not null,
    algorithm_id       bigint,
    execution_chain_id bigint,
    execution_date     timestamp,
    error_message      varchar(255),
    constraint lsfc_execution_pk primary key (id)
);

create sequence seq_lsfc_execution;

create index i1_lsfc_execution on lsfc_execution (algorithm_id);

create index i2_lsfc_execution on lsfc_execution (execution_chain_id);

--1.2.4 Таблица LSFC_InputData
create table if not exists lsfc_input_data
(
    id                      bigint not null,
    calc_input_parameter_id bigint,
    trigger_event_id        bigint,
    version_context_id      bigint,
    execution_id            bigint,
    output_data_id          bigint,
    bin_value               varbinary,
    constraint lsfc_input_data_pk primary key (id)
);

create sequence seq_lsfc_input_data;

create index i1_lsfc_input_data on lsfc_input_data (calc_input_parameter_id);

create index i2_lsfc_input_data on lsfc_input_data (trigger_event_id);

create index i3_lsfc_input_data on lsfc_input_data (version_context_id);

create index i4_lsfc_input_data on lsfc_input_data (execution_id);

create index i5_lsfc_input_data on lsfc_input_data (output_data_id);

--1.2.5 Таблица LSFC_OutputData
create table if not exists lsfc_output_data
(
    id                       bigint not null,
    execution_id             bigint,
    calc_output_parameter_id bigint,
    result                   varbinary,
    state                    tinyint,
    message                  varchar(255),
    constraint lsfc_output_data_pk primary key (id)
);

create sequence seq_lsfc_output_data;

create index i1_lsfc_output_data on lsfc_output_data (execution_id);

create index i2_lsfc_output_data on lsfc_output_data (calc_output_parameter_id);

--1.2.6 Таблица LSFC_AsyncFunctionExecution
create table if not exists lsfc_async_function_execution
(
    id           bigint not null,
    execution_id bigint,
    date         timestamp,
    state        tinyint,
    constraint lsfc_async_function_execution_pk primary key (id)
);

create sequence seq_lsfc_async_function_execution;

create index i1_lsfc_async_function_execution on lsfc_async_function_execution (execution_id);

--1.3.1 Таблица LSDM_Right
create table if not exists lsdm_right
(
    id                       bigint not null,
    kind_measure_id          bigint,
    recipient_id             bigint,
    execution_output_data_id bigint,
    state_right              tinyint,
    date                     date,
    constraint lsdm_right_pk primary key (id)
);

create sequence seq_lsdm_right;

create index i1_lsdm_right on lsdm_right (kind_measure_id);

create index i2_lsdm_right on lsdm_right (recipient_id);

create index i3_lsdm_right on lsdm_right (execution_output_data_id);

--1.3.2 Таблица LSDM_TargetMeasureAttributes
create table if not exists lsdm_target_measure_attributes
(
    id                        bigint not null,
    kind_measure_id           bigint,
    right_id                  bigint,
    execution_output_data_id  bigint,
    bin_value                 varbinary,
    measure_attribute_type_id bigint,
    constraint lsdm_target_measure_attributes_pk primary key (id)
);

create sequence seq_lsdm_target_measure_attributes;

create index i1_lsdm_target_measure_attributes on lsdm_target_measure_attributes (kind_measure_id);

create index i2_lsdm_target_measure_attributes on lsdm_target_measure_attributes (right_id);

create index i3_lsdm_target_measure_attributes on lsdm_target_measure_attributes (execution_output_data_id);

create index i4_lsdm_target_measure_attributes on lsdm_target_measure_attributes (measure_attribute_type_id);

--1.3.3 Таблица LSDM_LifeEvent

create table if not exists lsdm_life_event
(
    id                       bigint not null,
    recipient_id             bigint,
    execution_output_data_id bigint,
    life_event_type_id       bigint,
    date                     date,
    constraint lsdm_life_event_pk primary key (id)
);

create sequence seq_lsdm_life_event;

create index i1_lsdm_life_event on lsdm_life_event (recipient_id);

create index i2_lsdm_life_event on lsdm_life_event (execution_output_data_id);

create index i3_lsdm_life_event on lsdm_life_event (life_event_type_id);

--1.3.4 Таблица LSDM_PotentialRight
create table if not exists lsdm_potential_right
(
    id                       bigint not null,
    kind_measure_id          bigint,
    recipient_id             bigint,
    execution_output_data_id bigint,
    state_right              tinyint,
    date                     date,
    constraint lsdm_potential_right_pk primary key (id)
);

create sequence seq_lsdm_potential_right;

create index i1_lsdm_potential_right on lsdm_potential_right (kind_measure_id);

create index i2_lsdm_potential_right on lsdm_potential_right (recipient_id);

create index i3_lsdm_potential_right on lsdm_potential_right (execution_output_data_id);

--1.3.5 Таблица LSDM_CalculatedAttribute

create table if not exists lsdm_calculated_attribute
(
    id                        bigint not null,
    recipient_id              bigint,
    execution_output_data_id  bigint,
    bin_value                 varbinary,
    measure_attribute_type_id bigint,
    constraint lsdm_calculated_attribute_pk primary key (id)
);

create sequence seq_lsdm_calculated_attribute;

create index i1_lsdm_calculated_attribute on lsdm_calculated_attribute (recipient_id);

create index i2_lsdm_calculated_attribute on lsdm_calculated_attribute (execution_output_data_id);

create index i3_lsdm_calculated_attribute on lsdm_calculated_attribute (measure_attribute_type_id);

--1.4.1 Таблица LSMA_QueryExecution

create table if not exists lsma_query_execution
(
    id              bigint not null,
    kind_measure_id bigint,
    state           tinyint,
    date_begin      timestamp,
    date_end        timestamp,
    constraint llsma_query_execution_pk primary key (id)
);

create sequence seq_lsma_query_execution;

create index i1_lsma_query_execution on lsma_query_execution (kind_measure_id);

--1.4.2 Таблица LSMA_QueryExecutionRow

create table if not exists lsma_query_execution_row
(
    id                       bigint not null,
    right_id                 bigint,
    recipient_id             bigint,
    query_execution_id       bigint,
    execution_output_data_id bigint,
    constraint lsma_query_execution_row_pk primary key (id)
);

create sequence seq_lsma_query_execution_row;

create index i1_lsma_query_execution_row on lsma_query_execution_row (right_id);

create index i2_lsma_query_execution_row on lsma_query_execution_row (recipient_id);

create index i3_lsma_query_execution_row on lsma_query_execution_row (query_execution_id);

create index i4_lsma_query_execution_row on lsma_query_execution_row (execution_output_data_id);

--1.5.1 Таблица LSTS_TimerSchedule

create table if not exists lsts_timer_schedule
(
    id               bigint not null,
    configuration_id bigint,
    object_id        bigint,
    schedule_date    date,
    constraint lsts_timer_schedule_pk primary key (id)
);

create sequence seq_lsts_timer_schedule;

create index i1_lsts_timer_schedule on lsts_timer_schedule (configuration_id);

create index i2_lsts_timer_schedule on lsts_timer_schedule (object_id);
--1.5.2 Таблица LSTS_TimerExecution

create table if not exists lsts_timer_execution
(
    id                bigint not null,
    timer_schedule_id bigint,
    execution_date    timestamp,
    execution_state   tinyint,
    constraint lsts_timer_execution_pk primary key (id)
);

create sequence seq_lsts_timer_execution;

create index i1_lsts_timer_execution on lsts_timer_execution (timer_schedule_id);
