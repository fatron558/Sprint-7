--liquibase formatted sql

--changeset aanakireev:2

CREATE INDEX amount_index
ON account1 (amount);