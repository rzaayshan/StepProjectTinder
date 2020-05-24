create table users
(
    id serial
        constraint users_pk
            primary key,
    uname varchar,
    pass varchar,
    image varchar,
    name varchar,
    surname varchar
);
