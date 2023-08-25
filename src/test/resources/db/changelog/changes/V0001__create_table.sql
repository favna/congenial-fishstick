--liquibase formatted sql

--changeset ivt:V0001_a
CREATE SCHEMA example;
--rollback DROP SCHEMA example;

--changeset ivt:V0001_b
CREATE TABLE example.nicetable
(
    id                 BIGSERIAL PRIMARY KEY,
    name                VARCHAR(255) NOT NULL
) DISTRIBUTED BY (id);

CREATE INDEX nicetable_name_idx ON example.nicetable (name);
--rollback DROP TABLE example.nicetable;
