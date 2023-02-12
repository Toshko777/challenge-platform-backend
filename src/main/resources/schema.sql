DROP TABLE IF EXISTS Accounts CASCADE;
DROP TABLE IF EXISTS Roles CASCADE;
DROP TABLE IF EXISTS Accounts_Roles CASCADE;
DROP TABLE IF EXISTS Challenges CASCADE;
DROP TABLE IF EXISTS Completed_Challenge CASCADE;
DROP TABLE IF EXISTS Completed_Challenges CASCADE;
DROP TABLE IF EXISTS Badges CASCADE;

CREATE TABLE Accounts
(
    id         SERIAL       NOT NULL,
    username   varchar(30)  NOT NULL,
    first_name varchar(100) NOT NULL,
    last_name  varchar(100) NOT NULL,
    e_mail     varchar(100) NOT NULL,
    password   varchar(100) NOT NULL,
    badges     TEXT[],
    created    DATE DEFAULT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE Roles
(
    id          SERIAL      NOT NULL PRIMARY KEY,
    role        varchar(30) NOT NULL,
    permissions TEXT[]
);

CREATE TABLE Accounts_Roles
(
    id      SERIAL NOT NULL PRIMARY KEY,
    user_id INT    NOT NULL REFERENCES Accounts (id),
    role_id INT    NOT NULL REFERENCES Roles (id)
);

CREATE TABLE Challenges
(
    id          SERIAL       NOT NULL PRIMARY KEY,
    name        varchar(100) NOT NULL,
    description varchar(100) NOT NULL,
    creator_id  INT          NOT NULL REFERENCES Accounts (id),
    created     DATE DEFAULT NULL
);

CREATE TABLE Completed_Challenges
(
    user_id              INT          NOT NULL PRIMARY KEY REFERENCES Accounts (id),
    completed_challenges varchar(100) NOT NULL
);

CREATE TABLE Completed_Challenge
(
    id           SERIAL NOT NULL PRIMARY KEY,
    user_id      INT    NOT NULL REFERENCES Accounts (id),
    challenge_id INT    NOT NULL REFERENCES Challenges (id),
    started      DATE DEFAULT NULL,
    completed    DATE DEFAULT NULL
);

CREATE TABLE Badges
(
    id          SERIAL       NOT NULL,
    name        varchar(100) NOT NULL,
    description varchar(100) NOT NULL,
    condition   varchar(100) NOT NULL
)
