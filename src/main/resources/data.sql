INSERT INTO Accounts(username, first_name, last_name, e_mail, password, created)
values ('theadmin', 'admin', 'adminov', 'admin@gmail.com', 'admin', '2022-12-12');


INSERT INTO Roles(role, permissions)
values ('user', '{"create", "edit_self"}'),
       ('moderator', '{"create","edit","delete"}'),
       ('admin', '{"create","edit","delete", "change_role", "delete_user", "deactivate_user"}');


INSERT INTO Accounts_Roles(user_id, role_id)
values (1, 3);


-- INSERT INTO Challenges(name, description, creator, created)
-- values ('10k', 'walking 10k steps a day for 3 days', 'test_1', '2022-12-22');
--
-- INSERT INTO Completed_Challenges(user_id, challenge_id, started, completed)
-- values ('1', '1', '2022-12-22', '2022-12-25');