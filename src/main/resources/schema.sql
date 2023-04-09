DROP TABLE IF EXISTS accounts CASCADE;
DROP TABLE IF EXISTS roles CASCADE;
DROP TABLE IF EXISTS accounts_roles CASCADE;
DROP TABLE IF EXISTS challenges CASCADE;
DROP TABLE IF EXISTS account_completed_challenges CASCADE;
DROP TABLE IF EXISTS account_challenge CASCADE;
DROP TABLE IF EXISTS badges CASCADE;

CREATE TABLE accounts
(
    id         SERIAL       NOT NULL,
    username   varchar(30)  NOT NULL,
    first_name varchar(100) NOT NULL,
    last_name  varchar(100) NOT NULL,
    email      varchar(100) NOT NULL,
    password   varchar(100) NOT NULL,
    badges     INT[],
    created    DATE DEFAULT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE roles
(
    id   SERIAL      NOT NULL PRIMARY KEY,
    role varchar(30) NOT NULL
);

CREATE TABLE accounts_roles
(
    id         SERIAL NOT NULL PRIMARY KEY,
    account_id INT    NOT NULL REFERENCES accounts (id),
    role_id    INT    NOT NULL REFERENCES roles (id)
);

CREATE TABLE challenges
(
    id          SERIAL       NOT NULL PRIMARY KEY,
    name        varchar(100) NOT NULL,
    description varchar(100) NOT NULL,
    creator_id  INT          NOT NULL REFERENCES accounts (id),
    created     DATE DEFAULT NULL
);

CREATE TABLE account_completed_challenges
(
    id                   SERIAL NOT NULL PRIMARY KEY,
    account_id           INT    NOT NULL REFERENCES accounts (id),
    completed_challenges INT[] NOT NULL
);

CREATE TABLE account_challenge
(
    id           SERIAL NOT NULL PRIMARY KEY,
    account_id   INT    NOT NULL REFERENCES accounts (id),
    challenge_id INT    NOT NULL REFERENCES challenges (id),
    started      DATE DEFAULT NULL,
    completed    DATE DEFAULT NULL
);

CREATE TABLE badges
(
    id          SERIAL       NOT NULL,
    name        varchar(100) NOT NULL,
    description varchar(100) NOT NULL,
    condition   INT          NOT NULL
)
