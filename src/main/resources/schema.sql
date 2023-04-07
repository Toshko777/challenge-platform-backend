DROP TABLE IF EXISTS accounts CASCADE;
DROP TABLE IF EXISTS roles CASCADE;
DROP TABLE IF EXISTS accounts_roles CASCADE;
DROP TABLE IF EXISTS challenges CASCADE;
DROP TABLE IF EXISTS completed_challenge CASCADE;
DROP TABLE IF EXISTS completed_challenges CASCADE;
DROP TABLE IF EXISTS badges CASCADE;

CREATE TABLE accounts
(
    id         SERIAL       NOT NULL,
    username   varchar(30)  NOT NULL,
    first_name varchar(100) NOT NULL,
    last_name  varchar(100) NOT NULL,
    email      varchar(100) NOT NULL,
    password   varchar(100) NOT NULL,
    badges     TEXT[],
    created    DATE DEFAULT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE roles
(
    id          SERIAL      NOT NULL PRIMARY KEY,
    role        varchar(30) NOT NULL
);

CREATE TABLE accounts_roles
(
    id         SERIAL NOT NULL PRIMARY KEY,
    account_id INT    NOT NULL REFERENCES Accounts (id),
    role_id    INT    NOT NULL REFERENCES Roles (id)
);

CREATE TABLE challenges
(
    id          SERIAL       NOT NULL PRIMARY KEY,
    name        varchar(100) NOT NULL,
    description varchar(100) NOT NULL,
    creator_id  INT          NOT NULL REFERENCES Accounts (id),
    created     DATE DEFAULT NULL
);

CREATE TABLE account_completed_challenges
(
    account_id           INT          NOT NULL PRIMARY KEY REFERENCES Accounts (id),
    completed_challenges varchar(100) NOT NULL
);

CREATE TABLE account_challenge
(
    id           SERIAL NOT NULL PRIMARY KEY,
    account_id   INT    NOT NULL REFERENCES Accounts (id),
    challenge_id INT    NOT NULL REFERENCES Challenges (id),
    started      DATE DEFAULT NULL,
    completed    DATE DEFAULT NULL
);

CREATE TABLE badges
(
    id          SERIAL       NOT NULL,
    name        varchar(100) NOT NULL,
    description varchar(100) NOT NULL,
    condition   varchar(100) NOT NULL
)
