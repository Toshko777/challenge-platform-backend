INSERT INTO users(first_name, fam_name,  email, password, created)
values ('test', 'testov', 'test@test.com', 'parola123','2022-12-12'); 





INSERT INTO Accs(user_name, clearance_lvl)
values('test_1', 1);

INSERT INTO Clearance(clearance_position)
values('user'),
    ('moderator'),
        ('admin');


INSERT INTO CH_comp(user_name, challenge_name)
values('test_1', 'no chocolate for a month');

INSERT INTO CH_created(user_name, challenge_name)
values('test_1', 'walking 10k steps a day');