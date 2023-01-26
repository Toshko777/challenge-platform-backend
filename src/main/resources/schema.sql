DROP TABLE IF EXISTS Accs;
DROP TABLE IF EXISTS users;
-- DROP TABLE IF EXISTS Clearance;
-- DROP TABLE IF EXISTS CH_completed;
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
-- CREATE TABLE Clearance
-- (
--     id_c SERIAL NOT NULL,
--     clearance_position varchar(10) NOT NULL
--     PRIMARY KEY(id_c)
-- );


-- CREATE TABLE CH_completed
-- (
--     id_comp SERIAL NOT NULL,
--     created_by varchar(100) NOT NULL
--     PRIMARY KEY(id_comp)
-- );
