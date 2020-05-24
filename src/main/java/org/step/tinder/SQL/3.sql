create table messages
(
    id serial
        constraint messages_pk
            primary key,
    sender varchar,
    receiver varchar,
    message varchar,
    time varchar
);
