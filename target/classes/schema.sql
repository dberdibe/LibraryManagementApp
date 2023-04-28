drop table if exists persons, books;

create table persons(
    id int GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    firstname varchar NOT NULL,
    lastname varchar NOT NULL,
    yearOfBirth int NOT NULL
);

create table books(
    id int GENERATED BY DEFAULT AS IDENTITY NOT NULL PRIMARY KEY,
    name varchar NOT NULL,
    author varchar NOT NULL,
    year int NOT NULL
);