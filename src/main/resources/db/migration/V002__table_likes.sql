create table likes
(
    id serial
        constraint likes_pk
            primary key,
    who varchar,
    whom varchar
);