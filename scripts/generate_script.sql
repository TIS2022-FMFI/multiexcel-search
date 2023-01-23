use multiexcel;

SET FOREIGN_KEY_CHECKS = 0;
truncate table categories;
truncate table customers;
truncate table users;
truncate table queries;
truncate table categories_queries;
truncate table parts_queries;
truncate table parts;
truncate table drawings;
truncate table part_names;
SET FOREIGN_KEY_CHECKS = 1;


insert into multiexcel.users (user_name, password)
values ('admin', 'admin');

insert into categories (category_name)
values ('Without category');