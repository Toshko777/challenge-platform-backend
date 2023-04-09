INSERT INTO accounts(username, first_name, last_name, email, password, badges, created)
values ('theadmin', 'admin', 'adminov', 'admin@gmail.com',
        '$2a$10$/s0smkPdUwWRZfUdUOo09OWGN5SgUMM3R7mzi.vygRKBhLvRd12Tq',
        '{}', '2022-12-12'),
       ('theuser', 'user', 'userov', 'user@gmail.com', '$2a$10$nqWmWVtx96TI/KALdiEgluFRZQTklZKVccYI0bQllSEmYA8JIYYvu',
        '{}', '2022-12-12');
-- encode('admin001', 'base64') -> try it later
--values ('theuser', 'user', 'userov', 'user@gmail.com', encode('user001', 'base64'), '2022-12-12');


INSERT INTO roles(role)
values ('USER'),
       ('MODERATOR'),
       ('ADMIN');


INSERT INTO accounts_roles(account_id, role_id)
values (1, 3),
       (2, 1);


INSERT INTO challenges(name, description, creator_id, created)
values ('10k', 'walking 10k steps a day for 3 days', '1', '2022-12-22'),
       ('10x3', '10 pushups a day for 3 days', '2', '2022-12-23'),
       ('test1', 'run 5km', '1', '2022-12-24'),
       ('test2', 'run 5km', '1', '2022-12-21'),
       ('test3', 'run 5km', '1', '2022-12-22'),
       ('test4', 'run 5km', '1', '2022-12-23'),
       ('test5', 'run 5km', '1', '2022-12-24'),
       ('test6', 'run 5km', '1', '2022-12-25'),
       ('test7', 'run 5km', '1', '2022-12-26'),
       ('test8', 'run 5km', '1', '2022-12-27'),
       ('test9', 'run 5km', '1', '2022-12-28');


INSERT INTO account_challenge(account_id, challenge_id, started)
values (1, 1, '2022-12-22'),
       (1, 2, '2022-12-22'),
       (1, 3, '2022-12-22'),
       (2, 1, '2022-12-23');

INSERT INTO badges(name, description, condition)
values ('BEGINNER', 'Complete 3 or more challenges!', 3),
       ('INTERMEDIATE', 'Complete 6 or more challenges!', 6),
       ('PROFI', 'Complete 9 or more challenges!', 9);