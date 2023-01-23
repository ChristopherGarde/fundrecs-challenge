DROP TABLE IF EXISTS TRANSACTION;
CREATE TABLE TRANSACTION (
    trxdate date,
    trxtype varchar(10),
    amount numeric(100000,2),
    PRIMARY KEY (trxdate, trxtype)
);
