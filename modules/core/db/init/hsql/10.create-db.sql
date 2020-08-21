-- begin STAFF_EMPLOYEE
create table STAFF_EMPLOYEE (
    ID varchar(36) not null,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    NAME varchar(255),
    --
    primary key (ID)
)^
-- end STAFF_EMPLOYEE
-- begin STAFF_PROJECT
create table STAFF_PROJECT (
    ID varchar(36) not null,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    TITLE varchar(255) not null,
    --
    primary key (ID)
)^
-- end STAFF_PROJECT
-- begin STAFF_PROJECT_EMPLOYEE_LINK
create table STAFF_PROJECT_EMPLOYEE_LINK (
    PROJECT_ID varchar(36) not null,
    EMPLOYEE_ID varchar(36) not null,
    primary key (PROJECT_ID, EMPLOYEE_ID)
)^
-- end STAFF_PROJECT_EMPLOYEE_LINK
