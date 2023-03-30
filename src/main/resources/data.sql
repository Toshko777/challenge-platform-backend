INSERT INTO accounts(username, first_name, last_name, email, password, created)
values ('theadmin', 'admin', 'adminov', 'admin@gmail.com',
        '$2a$10$/s0smkPdUwWRZfUdUOo09OWGN5SgUMM3R7mzi.vygRKBhLvRd12Tq', '2022-12-12'),
       ('theuser', 'user', 'userov', 'user@gmail.com', '$2a$10$nqWmWVtx96TI/KALdiEgluFRZQTklZKVccYI0bQllSEmYA8JIYYvu',
        '2022-12-12');
-- encode('admin001', 'base64') -> try it later
--values ('theuser', 'user', 'userov', 'user@gmail.com', encode('user001', 'base64'), '2022-12-12');

-- moje bi chassta s permisionite da se mahne -> too much?!
INSERT INTO roles(role, permissions)
values ('USER', '{"create", "edit_self"}'),
       ('MODERATOR', '{"create","edit","delete"}'),
       ('ADMIN', '{"create","edit","delete", "change_role", "delete_user", "deactivate_user"}');


INSERT INTO accounts_roles(user_id, role_id)
values (1, 3),
       (2, 1);


-- INSERT INTO Challenges(name, description, creator, created)
-- values ('10k', 'walking 10k steps a day for 3 days', 'test_1', '2022-12-22');
--
-- INSERT INTO Completed_Challenges(user_id, challenge_id, started, completed)
-- values ('1', '1', '2022-12-22', '2022-12-25');