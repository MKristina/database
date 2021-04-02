create or replace procedure createTableIfNotExists (clauses varchar2) is
    alreadyExists exception;
    pragma exception_init (alreadyExists, -955);
begin
    execute immediate 'create table ' || clauses;
exception when alreadyExists then null;
end createTableIfNotExists;