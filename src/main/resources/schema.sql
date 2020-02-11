create table author(
    id IDENTITY NOT NULL PRIMARY KEY,
    username VARCHAR(255) NOT NULL,
    name VARCHAR(255) NOT NULL,
    bio CLOB
);

create table book(
    id IDENTITY NOT NULL PRIMARY KEY,
    author_id BIGINT NOT NULL,
    title VARCHAR(255),
    foreign key (author_id) references author(id)
);