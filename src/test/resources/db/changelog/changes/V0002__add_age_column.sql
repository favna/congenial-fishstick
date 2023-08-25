--liquibase formatted sql

--changeset ivt:V00002_a
ALTER TABLE example.nicetable
    ADD COLUMN age VARCHAR(255);

--rollback ALTER TABLE example.nicetable DROP COLUMN age;
