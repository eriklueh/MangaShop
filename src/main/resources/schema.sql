drop database manga;

create database manga;

create table author
(
    id          serial
        constraint author_pk
            primary key,
    name        varchar not null,
    last_name   varchar not null,
    nationality varchar not null,
    birth_date  varchar,
    death_date  varchar
);
alter table author
    owner to postgres;


create table volume
(
    id              serial
        constraint volume_pk
            primary key,
    number          integer not null,
    publishing_date date    not null,
    first_chapter   integer,
    last_chapter    integer
);
alter table volume
    owner to postgres;


create table manga
(
    id     serial
        constraint manga_pk
            primary key,
    title  varchar,
    genre  varchar not null,
    author integer not null
        constraint manga_author_id_fk
            references author
            on update cascade on delete cascade
);
alter table manga
    owner to postgres;


create table editorial
(
    id          serial
        constraint editorial_pk
            primary key,
    name        varchar not zxnull,
    nationality varchar not null,
    manga       integer
        constraint editorial_manga_id_fk
            references manga
            on update cascade on delete cascade
);
alter table editorial
    owner to postgres;

