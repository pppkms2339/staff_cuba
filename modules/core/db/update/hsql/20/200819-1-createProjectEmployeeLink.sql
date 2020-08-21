create table STAFF_PROJECT_EMPLOYEE_LINK (
    PROJECT_ID varchar(36) not null,
    EMPLOYEE_ID varchar(36) not null,
    primary key (PROJECT_ID, EMPLOYEE_ID)
);
