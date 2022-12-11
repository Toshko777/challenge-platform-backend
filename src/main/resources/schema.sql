DROP TABLE IF EXISTS users;
CREATE TABLE users
(
    id SERIAL NOT NULL,
    username varchar(100) NOT NULL,
    password varchar(100) NOT NULL,
    email varchar(100) NOT NULL,
    created  DATE DEFAULT NULL,
    PRIMARY KEY (id)
);
