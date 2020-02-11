create table if not exists author(
    id BIGINT NOT NULL AUTO_INCREMENT,
    username VARCHAR(255) NOT NULL,
    name VARCHAR(255) NOT NULL,
    bio TEXT,
    PRIMARY KEY (id)
);

create table if not exists book(
    id BIGINT NOT NULL AUTO_INCREMENT,
    author_id BIGINT NOT NULL,
    title VARCHAR(255),
    PRIMARY KEY (id),
    foreign key (author_id) references author(id)
);