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
       ('5km-challenge', 'run 5km', '1', '2022-12-24');


INSERT INTO account_challenge(account_id, challenge_id, started)
values (1, 1, '2022-12-22'),
       (1, 2, '2022-12-22'),
       (1, 3, '2022-12-22'),
       (2, 1, '2022-12-23');

INSERT INTO badges(name, description, condition)
values ('BEGINNER', 'Complete more than 3 challenges!', 3),
       ('INTERMEDIATE', 'Complete more than 6 challenges!', 6),
       ('PROFI', 'Complete more than 9 challenges!', 9);