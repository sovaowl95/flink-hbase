create table Msz
(
    id       text,
    personId text,
    bzMszId  text
);


create table MszStage
(
    id                       text,
    mszId                    text,
    bzMszTransactionStagesId text,
    bzMszStageId             text
);

create table MszStageParam
(
    id                text,
    mszStageId        text,
    bzMszStageParamId text,
    value             bytea
);

