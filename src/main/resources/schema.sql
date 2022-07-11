create database manga;

\c manga

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

create table editorial
(
    id          serial
        constraint editorial_pk
            primary key,
    name        varchar not null,
    nationality varchar not null
);

create table manga
(
    id        serial
        constraint manga_pk
            primary key,
    title     varchar,
    genre     varchar not null,
    author    integer not null
        constraint manga_author_id_fk
            references author
            on update cascade on delete cascade,
    editorial integer
        constraint manga_editorial_id_fk
            references editorial
            on update cascade on delete cascade
);

create table volume
(
    id              serial
        constraint volume_pk
            primary key,
    number          integer not null,
    publishing_date varchar not null,
    first_chapter   integer,
    last_chapter    integer,
    manga           integer
        constraint volume_manga_id_fk
            references manga
            on update cascade on delete cascade
);

