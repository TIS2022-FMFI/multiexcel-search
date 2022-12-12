SET
FOREIGN_KEY_CHECKS = 0;
truncate table users;
SET
FOREIGN_KEY_CHECKS = 1;

insert into multiexcel.users (user_name, password)
values ('admin', 'admin');

insert into multiexcel.users (user_name, password)
values ('user', 'user');

