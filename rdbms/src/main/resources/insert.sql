--liquibase formatted sql

--changeset aanakireev:1

insert into account1(id, amount, version)
values (1, 50, 1),
       (2, 100, 1)