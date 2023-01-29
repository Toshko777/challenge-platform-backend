DROP TABLE IF EXISTS Accs;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS Clearance;
DROP TABLE IF EXISTS CH_comp;
DROP TABLE IF EXISTS CH_created;

CREATE TABLE Accs
 (
   id SERIAL NOT NULL,
    user_name varchar(100) NOT NULL,
    clearance_lvl int NOT NULL,
     PRIMARY KEY (id) 
   --FOREIGN KEY(clearance_lvl) REFERENCES Clearance(id_c)
);
CREATE TABLE users
(
    id SERIAL NOT NULL,
    first_name varchar(100) NOT NULL,
    fam_name varchar(100) NOT NULL,
    email varchar(100) NOT NULL,
    password varchar(100) NOT NULL,
    created  DATE DEFAULT NULL,
    
    PRIMARY KEY (id) 
    -- FOREIGN KEY(id_u) 
    -- FOREIGN KEY(id_challengge_compl) REFERENCES CH_completed(id_comp)
    
);
CREATE TABLE Clearance
(
    id SERIAL NOT NULL,
    clearance_position varchar(10) NOT NULL,
    PRIMARY KEY(id)
);


CREATE TABLE CH_comp
 (
     id SERIAL NOT NULL,
     user_name varchar(100) NOT NULL,
     challenge_name varchar(100) NOT NULL,
     PRIMARY KEY(id)
     --FOREIGN KEY(user_name) REFERENCES Accs(user_name)
 );
 CREATE TABLE CH_created
 (
     id SERIAL NOT NULL,
     user_name varchar(100) NOT NULL,
     challenge_name varchar(100) NOT NULL,
     PRIMARY KEY(id)
     --FOREIGN KEY(user_name) REFERENCES Accs(user_name)
 );
