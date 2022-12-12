#drop database if exists Multiexcel;
#create database Multiexcel;

SET FOREIGN_KEY_CHECKS = 0;
drop table if exists users cascade;
drop table if exists queries cascade;
drop table if exists part_name_queries cascade;
drop table if exists categories_queries;
drop table if exists customers_queries;
drop table if exists parts_queries;
drop table if exists categories;
drop table if exists part_names;
drop table if exists customers;
drop table if exists parts;
drop table if exists drawings;
SET FOREIGN_KEY_CHECKS = 1;


create table users
(
    user_id   serial primary key,
    user_name varchar(30) not null,
    password  varchar(20) not null,
    suspended boolean default 0
);

create table queries
(
    query_id    serial primary key,
    user_id     bigint unsigned not null,
    rubber      smallint,
    diameter_AT decimal,
    length_L_AT decimal,
    diameter_IT decimal,
    length_L_IT decimal,
    diameter_ZT decimal,
    length_L_ZT decimal,
    cr_steg     mediumint,
    cr_niere    smallint,
    ca          smallint,
    ct          decimal,
    ck          decimal,
    date        datetime        not null
);

create table part_name_queries
(
    part_name_query_id serial primary key,
    part_name_id       bigint unsigned not null,
    query_id           bigint unsigned not null
);

create table categories_queries
(
    category_query_id serial primary key,
    category_id       bigint unsigned not null,
    query_id          bigint unsigned not null
);

create table customers_queries
(
    customer_query_id serial primary key,
    customer_id       bigint unsigned not null,
    query_id          bigint unsigned not null
);

create table parts_queries
(
    part_query_id serial primary key,
    part_number   varchar(30)     not null,
    query_id      bigint unsigned not null
);

create table part_names
(
    part_name_id serial primary key,
    part_name    varchar(50) not null
);

create table categories
(
    category_id   serial primary key,
    category_name varchar(50) not null
);

create table customers
(
    customer_id   serial primary key,
    customer_name varchar(50) not null
);

create table parts
(
    part_number     varchar(30) primary key,
    customer_id     bigint unsigned,
    part_name_id    bigint unsigned,
    category_id     bigint unsigned,
    drawing_id      bigint unsigned,
    rubber          smallint,
    diameter_AT     decimal,
    diameter_AT_tol varchar(20),
    length_L_AT     decimal,
    length_L_AT_tol varchar(20),
    diameter_IT     decimal,
    diameter_IT_tol varchar(20),
    length_L_IT     decimal,
    length_L_IT_tol varchar(20),
    diameter_ZT     decimal,
    diameter_ZT_tol varchar(20),
    length_L_ZT     decimal,
    length_L_ZT_tol varchar(20),
    cr_steg         mediumint,
    cr_niere        smallint,
    ca              smallint,
    ct              decimal,
    ck              decimal,
    rating          int default 0
);

create table drawings
(
    drawing_id serial primary key,
    drawing    longblob not null
);


alter table queries
    add foreign key (user_id)
        references users (user_id)
        on delete cascade;

alter table part_name_queries
    add foreign key (part_name_id)
        references part_names (part_name_id)
        on delete cascade,
    add foreign key (query_id)
        references queries (query_id)
        on delete cascade;

alter table categories_queries
    add foreign key (category_id)
        references categories (category_id)
        on delete cascade,
    add foreign key (query_id)
        references queries (query_id)
        on delete cascade;

alter table customers_queries
    add foreign key (customer_id)
        references customers (customer_id)
        on delete cascade,
    add foreign key (query_id)
        references queries (query_id)
        on delete cascade;

alter table parts_queries
    add foreign key (part_number)
        references parts (part_number)
        on delete cascade,
    add foreign key (query_id)
        references queries (query_id)
        on delete cascade;

alter table parts
    add foreign key (customer_id)
        references customers (customer_id)
        on delete set null,
    add foreign key (part_name_id)
        references part_names (part_name_id)
        on delete set null,
    add foreign key (category_id)
        references categories (category_id)
        on delete set null,
    add foreign key (drawing_id)
        references drawings (drawing_id)
        on delete set null;


create index rubber on parts (rubber);
create index diameter_AT on parts (diameter_AT);
create index length_L_AT on parts (length_L_AT);
create index diameter_IT on parts (diameter_IT);
create index length_L_IT on parts (length_L_IT);
create index diameter_ZT on parts (diameter_ZT);
create index length_L_ZT on parts (length_L_ZT);
create index cr_steg on parts (cr_steg);
create index cr_niere on parts (cr_niere);
create index ca on parts (ca);
create index ct on parts (ct);
create index ck on parts (ck);

create index date on queries (date);