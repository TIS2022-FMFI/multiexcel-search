SET
FOREIGN_KEY_CHECKS = 0;
truncate table users;
SET
FOREIGN_KEY_CHECKS = 1;

insert into multiexcel.users (user_name, password)
values ('admin', 'admin');

insert into multiexcel.users (user_name, password)
values ('user', 'user');

# test data for Query table
insert into multiexcel.queries(user_id, rubber_from, rubber_to, diameter_AT_from, diameter_AT_to, length_L_AT_from, length_L_AT_to, diameter_IT_from, diameter_IT_to, length_L_IT_from, length_L_IT_to, diameter_ZT_from, diameter_ZT_to, length_L_ZT_from, length_L_ZT_to, cr_steg_from, cr_steg_to, cr_niere_from, cr_niere_to, ca_from, ca_to, ct_from, ct_to, ck_from, ck_to, date)
values(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, date('2022-12-26'));

insert into multiexcel.queries(user_id, rubber_from, rubber_to, diameter_AT_from, diameter_AT_to, length_L_AT_from, length_L_AT_to, diameter_IT_from, diameter_IT_to, length_L_IT_from, length_L_IT_to, diameter_ZT_from, diameter_ZT_to, length_L_ZT_from, length_L_ZT_to, cr_steg_from, cr_steg_to, cr_niere_from, cr_niere_to, ca_from, ca_to, ct_from, ct_to, ck_from, ck_to, date)
values(2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, date('2022-12-25'));

insert into multiexcel.queries(user_id, rubber_from, rubber_to, diameter_AT_from, diameter_AT_to, length_L_AT_from, length_L_AT_to, diameter_IT_from, diameter_IT_to, length_L_IT_from, length_L_IT_to, diameter_ZT_from, diameter_ZT_to, length_L_ZT_from, length_L_ZT_to, cr_steg_from, cr_steg_to, cr_niere_from, cr_niere_to, ca_from, ca_to, ct_from, ct_to, ck_from, ck_to, date)
values(1, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, date('2022-12-24'));

insert into multiexcel.queries(user_id, rubber_from, rubber_to, diameter_AT_from, diameter_AT_to, length_L_AT_from, length_L_AT_to, diameter_IT_from, diameter_IT_to, length_L_IT_from, length_L_IT_to, diameter_ZT_from, diameter_ZT_to, length_L_ZT_from, length_L_ZT_to, cr_steg_from, cr_steg_to, cr_niere_from, cr_niere_to, ca_from, ca_to, ct_from, ct_to, ck_from, ck_to, date)
values(2, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, date('2022-12-23'));

insert into multiexcel.queries(user_id, rubber_from, rubber_to, diameter_AT_from, diameter_AT_to, length_L_AT_from, length_L_AT_to, diameter_IT_from, diameter_IT_to, length_L_IT_from, length_L_IT_to, diameter_ZT_from, diameter_ZT_to, length_L_ZT_from, length_L_ZT_to, cr_steg_from, cr_steg_to, cr_niere_from, cr_niere_to, ca_from, ca_to, ct_from, ct_to, ck_from, ck_to, date)
values(1, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, date('2022-12-22'));

insert into multiexcel.queries(user_id, rubber_from, rubber_to, diameter_AT_from, diameter_AT_to, length_L_AT_from, length_L_AT_to, diameter_IT_from, diameter_IT_to, length_L_IT_from, length_L_IT_to, diameter_ZT_from, diameter_ZT_to, length_L_ZT_from, length_L_ZT_to, cr_steg_from, cr_steg_to, cr_niere_from, cr_niere_to, ca_from, ca_to, ct_from, ct_to, ck_from, ck_to, date)
values(2, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, date('2022-12-21'));

insert into multiexcel.queries(user_id, rubber_from, rubber_to, diameter_AT_from, diameter_AT_to, length_L_AT_from, length_L_AT_to, diameter_IT_from, diameter_IT_to, length_L_IT_from, length_L_IT_to, diameter_ZT_from, diameter_ZT_to, length_L_ZT_from, length_L_ZT_to, cr_steg_from, cr_steg_to, cr_niere_from, cr_niere_to, ca_from, ca_to, ct_from, ct_to, ck_from, ck_to, date)
values(1, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, date('2022-12-20'));

insert into multiexcel.queries(user_id, rubber_from, rubber_to, diameter_AT_from, diameter_AT_to, length_L_AT_from, length_L_AT_to, diameter_IT_from, diameter_IT_to, length_L_IT_from, length_L_IT_to, diameter_ZT_from, diameter_ZT_to, length_L_ZT_from, length_L_ZT_to, cr_steg_from, cr_steg_to, cr_niere_from, cr_niere_to, ca_from, ca_to, ct_from, ct_to, ck_from, ck_to, date)
values(2, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, date('2022-12-19'));

insert into multiexcel.queries(user_id, rubber_from, rubber_to, diameter_AT_from, diameter_AT_to, length_L_AT_from, length_L_AT_to, diameter_IT_from, diameter_IT_to, length_L_IT_from, length_L_IT_to, diameter_ZT_from, diameter_ZT_to, length_L_ZT_from, length_L_ZT_to, cr_steg_from, cr_steg_to, cr_niere_from, cr_niere_to, ca_from, ca_to, ct_from, ct_to, ck_from, ck_to, date)
values(1, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, date('2022-12-18'));

insert into multiexcel.queries(user_id, rubber_from, rubber_to, diameter_AT_from, diameter_AT_to, length_L_AT_from, length_L_AT_to, diameter_IT_from, diameter_IT_to, length_L_IT_from, length_L_IT_to, diameter_ZT_from, diameter_ZT_to, length_L_ZT_from, length_L_ZT_to, cr_steg_from, cr_steg_to, cr_niere_from, cr_niere_to, ca_from, ca_to, ct_from, ct_to, ck_from, ck_to, date)
values(2, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, date('2022-12-17'));

insert into multiexcel.queries(user_id, rubber_from, rubber_to, diameter_AT_from, diameter_AT_to, length_L_AT_from, length_L_AT_to, diameter_IT_from, diameter_IT_to, length_L_IT_from, length_L_IT_to, diameter_ZT_from, diameter_ZT_to, length_L_ZT_from, length_L_ZT_to, cr_steg_from, cr_steg_to, cr_niere_from, cr_niere_to, ca_from, ca_to, ct_from, ct_to, ck_from, ck_to, date)
values(1, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, date('2022-12-16'));

insert into multiexcel.queries(user_id, rubber_from, rubber_to, diameter_AT_from, diameter_AT_to, length_L_AT_from, length_L_AT_to, diameter_IT_from, diameter_IT_to, length_L_IT_from, length_L_IT_to, diameter_ZT_from, diameter_ZT_to, length_L_ZT_from, length_L_ZT_to, cr_steg_from, cr_steg_to, cr_niere_from, cr_niere_to, ca_from, ca_to, ct_from, ct_to, ck_from, ck_to, date)
values(2, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, date('2022-12-15'));

insert into multiexcel.queries(user_id, rubber_from, rubber_to, diameter_AT_from, diameter_AT_to, length_L_AT_from, length_L_AT_to, diameter_IT_from, diameter_IT_to, length_L_IT_from, length_L_IT_to, diameter_ZT_from, diameter_ZT_to, length_L_ZT_from, length_L_ZT_to, cr_steg_from, cr_steg_to, cr_niere_from, cr_niere_to, ca_from, ca_to, ct_from, ct_to, ck_from, ck_to, date)
values(1, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, date('2022-12-14'));

insert into multiexcel.queries(user_id, rubber_from, rubber_to, diameter_AT_from, diameter_AT_to, length_L_AT_from, length_L_AT_to, diameter_IT_from, diameter_IT_to, length_L_IT_from, length_L_IT_to, diameter_ZT_from, diameter_ZT_to, length_L_ZT_from, length_L_ZT_to, cr_steg_from, cr_steg_to, cr_niere_from, cr_niere_to, ca_from, ca_to, ct_from, ct_to, ck_from, ck_to, date)
values(2, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, date('2022-12-13'));

insert into multiexcel.queries(user_id, rubber_from, rubber_to, diameter_AT_from, diameter_AT_to, length_L_AT_from, length_L_AT_to, diameter_IT_from, diameter_IT_to, length_L_IT_from, length_L_IT_to, diameter_ZT_from, diameter_ZT_to, length_L_ZT_from, length_L_ZT_to, cr_steg_from, cr_steg_to, cr_niere_from, cr_niere_to, ca_from, ca_to, ct_from, ct_to, ck_from, ck_to, date)
values(1, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, date('2022-12-12'));

insert into multiexcel.queries(user_id, rubber_from, rubber_to, diameter_AT_from, diameter_AT_to, length_L_AT_from, length_L_AT_to, diameter_IT_from, diameter_IT_to, length_L_IT_from, length_L_IT_to, diameter_ZT_from, diameter_ZT_to, length_L_ZT_from, length_L_ZT_to, cr_steg_from, cr_steg_to, cr_niere_from, cr_niere_to, ca_from, ca_to, ct_from, ct_to, ck_from, ck_to, date)
values(2, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, date('2022-12-11'));

insert into multiexcel.queries(user_id, rubber_from, rubber_to, diameter_AT_from, diameter_AT_to, length_L_AT_from, length_L_AT_to, diameter_IT_from, diameter_IT_to, length_L_IT_from, length_L_IT_to, diameter_ZT_from, diameter_ZT_to, length_L_ZT_from, length_L_ZT_to, cr_steg_from, cr_steg_to, cr_niere_from, cr_niere_to, ca_from, ca_to, ct_from, ct_to, ck_from, ck_to, date)
values(1, 17, 17, 17, 17, 17, 17, 17, 17, 17, 17, 17, 17, 17, 17, 17, 17, 17, 17, 17, 17, 17, 17, 17, 17, date('2022-12-10'));

insert into multiexcel.queries(user_id, rubber_from, rubber_to, diameter_AT_from, diameter_AT_to, length_L_AT_from, length_L_AT_to, diameter_IT_from, diameter_IT_to, length_L_IT_from, length_L_IT_to, diameter_ZT_from, diameter_ZT_to, length_L_ZT_from, length_L_ZT_to, cr_steg_from, cr_steg_to, cr_niere_from, cr_niere_to, ca_from, ca_to, ct_from, ct_to, ck_from, ck_to, date)
values(2, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, date('2022-12-09'));

insert into multiexcel.queries(user_id, rubber_from, rubber_to, diameter_AT_from, diameter_AT_to, length_L_AT_from, length_L_AT_to, diameter_IT_from, diameter_IT_to, length_L_IT_from, length_L_IT_to, diameter_ZT_from, diameter_ZT_to, length_L_ZT_from, length_L_ZT_to, cr_steg_from, cr_steg_to, cr_niere_from, cr_niere_to, ca_from, ca_to, ct_from, ct_to, ck_from, ck_to, date)
values(1, 19, 19, 19, 19, 19, 19, 19, 19, 19, 19, 19, 19, 19, 19, 19, 19, 19, 19, 19, 19, 19, 19, 19, 19, date('2022-12-08'));

insert into multiexcel.queries(user_id, rubber_from, rubber_to, diameter_AT_from, diameter_AT_to, length_L_AT_from, length_L_AT_to, diameter_IT_from, diameter_IT_to, length_L_IT_from, length_L_IT_to, diameter_ZT_from, diameter_ZT_to, length_L_ZT_from, length_L_ZT_to, cr_steg_from, cr_steg_to, cr_niere_from, cr_niere_to, ca_from, ca_to, ct_from, ct_to, ck_from, ck_to, date)
values(2, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, date('2022-12-07'));

insert into multiexcel.queries(user_id, rubber_from, rubber_to, diameter_AT_from, diameter_AT_to, length_L_AT_from, length_L_AT_to, diameter_IT_from, diameter_IT_to, length_L_IT_from, length_L_IT_to, diameter_ZT_from, diameter_ZT_to, length_L_ZT_from, length_L_ZT_to, cr_steg_from, cr_steg_to, cr_niere_from, cr_niere_to, ca_from, ca_to, ct_from, ct_to, ck_from, ck_to, date)
values(1, 21, 21, 21, 21, 21, 21, 21, 21, 21, 21, 21, 21, 21, 21, 21, 21, 21, 21, 21, 21, 21, 21, 21, 21, date('2022-12-06'));

insert into multiexcel.queries(user_id, rubber_from, rubber_to, diameter_AT_from, diameter_AT_to, length_L_AT_from, length_L_AT_to, diameter_IT_from, diameter_IT_to, length_L_IT_from, length_L_IT_to, diameter_ZT_from, diameter_ZT_to, length_L_ZT_from, length_L_ZT_to, cr_steg_from, cr_steg_to, cr_niere_from, cr_niere_to, ca_from, ca_to, ct_from, ct_to, ck_from, ck_to, date)
values(2, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, date('2022-12-05'));

insert into multiexcel.queries(user_id, rubber_from, rubber_to, diameter_AT_from, diameter_AT_to, length_L_AT_from, length_L_AT_to, diameter_IT_from, diameter_IT_to, length_L_IT_from, length_L_IT_to, diameter_ZT_from, diameter_ZT_to, length_L_ZT_from, length_L_ZT_to, cr_steg_from, cr_steg_to, cr_niere_from, cr_niere_to, ca_from, ca_to, ct_from, ct_to, ck_from, ck_to, date)
values(1, 23, 23, 23, 23, 23, 23, 23, 23, 23, 23, 23, 23, 23, 23, 23, 23, 23, 23, 23, 23, 23, 23, 23, 23, date('2022-12-04'));

insert into multiexcel.queries(user_id, rubber_from, rubber_to, diameter_AT_from, diameter_AT_to, length_L_AT_from, length_L_AT_to, diameter_IT_from, diameter_IT_to, length_L_IT_from, length_L_IT_to, diameter_ZT_from, diameter_ZT_to, length_L_ZT_from, length_L_ZT_to, cr_steg_from, cr_steg_to, cr_niere_from, cr_niere_to, ca_from, ca_to, ct_from, ct_to, ck_from, ck_to, date)
values(2, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, date('2022-12-03'));

insert into multiexcel.queries(user_id, rubber_from, rubber_to, diameter_AT_from, diameter_AT_to, length_L_AT_from, length_L_AT_to, diameter_IT_from, diameter_IT_to, length_L_IT_from, length_L_IT_to, diameter_ZT_from, diameter_ZT_to, length_L_ZT_from, length_L_ZT_to, cr_steg_from, cr_steg_to, cr_niere_from, cr_niere_to, ca_from, ca_to, ct_from, ct_to, ck_from, ck_to, date)
values(1, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, date('2022-12-02'));

insert into multiexcel.queries(user_id, rubber_from, rubber_to, diameter_AT_from, diameter_AT_to, length_L_AT_from, length_L_AT_to, diameter_IT_from, diameter_IT_to, length_L_IT_from, length_L_IT_to, diameter_ZT_from, diameter_ZT_to, length_L_ZT_from, length_L_ZT_to, cr_steg_from, cr_steg_to, cr_niere_from, cr_niere_to, ca_from, ca_to, ct_from, ct_to, ck_from, ck_to, date)
values(2, 26, 26, 26, 26, 26, 26, 26, 26, 26, 26, 26, 26, 26, 26, 26, 26, 26, 26, 26, 26, 26, 26, 26, 26, date('2022-12-01'));

insert into multiexcel.queries(user_id, rubber_from, rubber_to, diameter_AT_from, diameter_AT_to, length_L_AT_from, length_L_AT_to, diameter_IT_from, diameter_IT_to, length_L_IT_from, length_L_IT_to, diameter_ZT_from, diameter_ZT_to, length_L_ZT_from, length_L_ZT_to, cr_steg_from, cr_steg_to, cr_niere_from, cr_niere_to, ca_from, ca_to, ct_from, ct_to, ck_from, ck_to, date)
values(1, 27, 27, 27, 27, 27, 27, 27, 27, 27, 27, 27, 27, 27, 27, 27, 27, 27, 27, 27, 27, 27, 27, 27, 27, date('2022-11-30'));

insert into multiexcel.queries(user_id, rubber_from, rubber_to, diameter_AT_from, diameter_AT_to, length_L_AT_from, length_L_AT_to, diameter_IT_from, diameter_IT_to, length_L_IT_from, length_L_IT_to, diameter_ZT_from, diameter_ZT_to, length_L_ZT_from, length_L_ZT_to, cr_steg_from, cr_steg_to, cr_niere_from, cr_niere_to, ca_from, ca_to, ct_from, ct_to, ck_from, ck_to, date)
values(2, 28, 28, 28, 28, 28, 28, 28, 28, 28, 28, 28, 28, 28, 28, 28, 28, 28, 28, 28, 28, 28, 28, 28, 28, date('2022-11-29'));

insert into multiexcel.queries(user_id, rubber_from, rubber_to, diameter_AT_from, diameter_AT_to, length_L_AT_from, length_L_AT_to, diameter_IT_from, diameter_IT_to, length_L_IT_from, length_L_IT_to, diameter_ZT_from, diameter_ZT_to, length_L_ZT_from, length_L_ZT_to, cr_steg_from, cr_steg_to, cr_niere_from, cr_niere_to, ca_from, ca_to, ct_from, ct_to, ck_from, ck_to, date)
values(1, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, date('2022-11-28'));

insert into multiexcel.queries(user_id, rubber_from, rubber_to, diameter_AT_from, diameter_AT_to, length_L_AT_from, length_L_AT_to, diameter_IT_from, diameter_IT_to, length_L_IT_from, length_L_IT_to, diameter_ZT_from, diameter_ZT_to, length_L_ZT_from, length_L_ZT_to, cr_steg_from, cr_steg_to, cr_niere_from, cr_niere_to, ca_from, ca_to, ct_from, ct_to, ck_from, ck_to, date)
values(2, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, date('2022-11-27'));

insert into multiexcel.queries(user_id, rubber_from, rubber_to, diameter_AT_from, diameter_AT_to, length_L_AT_from, length_L_AT_to, diameter_IT_from, diameter_IT_to, length_L_IT_from, length_L_IT_to, diameter_ZT_from, diameter_ZT_to, length_L_ZT_from, length_L_ZT_to, cr_steg_from, cr_steg_to, cr_niere_from, cr_niere_to, ca_from, ca_to, ct_from, ct_to, ck_from, ck_to, date)
values(1, 31, 31, 31, 31, 31, 31, 31, 31, 31, 31, 31, 31, 31, 31, 31, 31, 31, 31, 31, 31, 31, 31, 31, 31, date('2022-11-26'));

insert into multiexcel.queries(user_id, rubber_from, rubber_to, diameter_AT_from, diameter_AT_to, length_L_AT_from, length_L_AT_to, diameter_IT_from, diameter_IT_to, length_L_IT_from, length_L_IT_to, diameter_ZT_from, diameter_ZT_to, length_L_ZT_from, length_L_ZT_to, cr_steg_from, cr_steg_to, cr_niere_from, cr_niere_to, ca_from, ca_to, ct_from, ct_to, ck_from, ck_to, date)
values(2, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, date('2022-11-25'));

insert into multiexcel.queries(user_id, rubber_from, rubber_to, diameter_AT_from, diameter_AT_to, length_L_AT_from, length_L_AT_to, diameter_IT_from, diameter_IT_to, length_L_IT_from, length_L_IT_to, diameter_ZT_from, diameter_ZT_to, length_L_ZT_from, length_L_ZT_to, cr_steg_from, cr_steg_to, cr_niere_from, cr_niere_to, ca_from, ca_to, ct_from, ct_to, ck_from, ck_to, date)
values(1, 33, 33, 33, 33, 33, 33, 33, 33, 33, 33, 33, 33, 33, 33, 33, 33, 33, 33, 33, 33, 33, 33, 33, 33, date('2022-11-24'));

insert into multiexcel.queries(user_id, rubber_from, rubber_to, diameter_AT_from, diameter_AT_to, length_L_AT_from, length_L_AT_to, diameter_IT_from, diameter_IT_to, length_L_IT_from, length_L_IT_to, diameter_ZT_from, diameter_ZT_to, length_L_ZT_from, length_L_ZT_to, cr_steg_from, cr_steg_to, cr_niere_from, cr_niere_to, ca_from, ca_to, ct_from, ct_to, ck_from, ck_to, date)
values(2, 34, 34, 34, 34, 34, 34, 34, 34, 34, 34, 34, 34, 34, 34, 34, 34, 34, 34, 34, 34, 34, 34, 34, 34, date('2022-11-23'));

insert into multiexcel.queries(user_id, rubber_from, rubber_to, diameter_AT_from, diameter_AT_to, length_L_AT_from, length_L_AT_to, diameter_IT_from, diameter_IT_to, length_L_IT_from, length_L_IT_to, diameter_ZT_from, diameter_ZT_to, length_L_ZT_from, length_L_ZT_to, cr_steg_from, cr_steg_to, cr_niere_from, cr_niere_to, ca_from, ca_to, ct_from, ct_to, ck_from, ck_to, date)
values(1, 35, 35, 35, 35, 35, 35, 35, 35, 35, 35, 35, 35, 35, 35, 35, 35, 35, 35, 35, 35, 35, 35, 35, 35, date('2022-11-22'));

insert into multiexcel.queries(user_id, rubber_from, rubber_to, diameter_AT_from, diameter_AT_to, length_L_AT_from, length_L_AT_to, diameter_IT_from, diameter_IT_to, length_L_IT_from, length_L_IT_to, diameter_ZT_from, diameter_ZT_to, length_L_ZT_from, length_L_ZT_to, cr_steg_from, cr_steg_to, cr_niere_from, cr_niere_to, ca_from, ca_to, ct_from, ct_to, ck_from, ck_to, date)
values(2, 36, 36, 36, 36, 36, 36, 36, 36, 36, 36, 36, 36, 36, 36, 36, 36, 36, 36, 36, 36, 36, 36, 36, 36, date('2022-11-21'));

insert into multiexcel.queries(user_id, rubber_from, rubber_to, diameter_AT_from, diameter_AT_to, length_L_AT_from, length_L_AT_to, diameter_IT_from, diameter_IT_to, length_L_IT_from, length_L_IT_to, diameter_ZT_from, diameter_ZT_to, length_L_ZT_from, length_L_ZT_to, cr_steg_from, cr_steg_to, cr_niere_from, cr_niere_to, ca_from, ca_to, ct_from, ct_to, ck_from, ck_to, date)
values(1, 37, 37, 37, 37, 37, 37, 37, 37, 37, 37, 37, 37, 37, 37, 37, 37, 37, 37, 37, 37, 37, 37, 37, 37, date('2022-11-20'));

insert into multiexcel.queries(user_id, rubber_from, rubber_to, diameter_AT_from, diameter_AT_to, length_L_AT_from, length_L_AT_to, diameter_IT_from, diameter_IT_to, length_L_IT_from, length_L_IT_to, diameter_ZT_from, diameter_ZT_to, length_L_ZT_from, length_L_ZT_to, cr_steg_from, cr_steg_to, cr_niere_from, cr_niere_to, ca_from, ca_to, ct_from, ct_to, ck_from, ck_to, date)
values(2, 38, 38, 38, 38, 38, 38, 38, 38, 38, 38, 38, 38, 38, 38, 38, 38, 38, 38, 38, 38, 38, 38, 38, 38, date('2022-11-19'));

insert into multiexcel.queries(user_id, rubber_from, rubber_to, diameter_AT_from, diameter_AT_to, length_L_AT_from, length_L_AT_to, diameter_IT_from, diameter_IT_to, length_L_IT_from, length_L_IT_to, diameter_ZT_from, diameter_ZT_to, length_L_ZT_from, length_L_ZT_to, cr_steg_from, cr_steg_to, cr_niere_from, cr_niere_to, ca_from, ca_to, ct_from, ct_to, ck_from, ck_to, date)
values(1, 39, 39, 39, 39, 39, 39, 39, 39, 39, 39, 39, 39, 39, 39, 39, 39, 39, 39, 39, 39, 39, 39, 39, 39, date('2022-11-18'));

insert into multiexcel.queries(user_id, rubber_from, rubber_to, diameter_AT_from, diameter_AT_to, length_L_AT_from, length_L_AT_to, diameter_IT_from, diameter_IT_to, length_L_IT_from, length_L_IT_to, diameter_ZT_from, diameter_ZT_to, length_L_ZT_from, length_L_ZT_to, cr_steg_from, cr_steg_to, cr_niere_from, cr_niere_to, ca_from, ca_to, ct_from, ct_to, ck_from, ck_to, date)
values(2, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, date('2022-11-17'));

insert into multiexcel.queries(user_id, rubber_from, rubber_to, diameter_AT_from, diameter_AT_to, length_L_AT_from, length_L_AT_to, diameter_IT_from, diameter_IT_to, length_L_IT_from, length_L_IT_to, diameter_ZT_from, diameter_ZT_to, length_L_ZT_from, length_L_ZT_to, cr_steg_from, cr_steg_to, cr_niere_from, cr_niere_to, ca_from, ca_to, ct_from, ct_to, ck_from, ck_to, date)
values(1, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, date('2022-11-16'));

insert into multiexcel.queries(user_id, rubber_from, rubber_to, diameter_AT_from, diameter_AT_to, length_L_AT_from, length_L_AT_to, diameter_IT_from, diameter_IT_to, length_L_IT_from, length_L_IT_to, diameter_ZT_from, diameter_ZT_to, length_L_ZT_from, length_L_ZT_to, cr_steg_from, cr_steg_to, cr_niere_from, cr_niere_to, ca_from, ca_to, ct_from, ct_to, ck_from, ck_to, date)
values(2, 42, 42, 42, 42, 42, 42, 42, 42, 42, 42, 42, 42, 42, 42, 42, 42, 42, 42, 42, 42, 42, 42, 42, 42, date('2022-11-15'));

insert into multiexcel.queries(user_id, rubber_from, rubber_to, diameter_AT_from, diameter_AT_to, length_L_AT_from, length_L_AT_to, diameter_IT_from, diameter_IT_to, length_L_IT_from, length_L_IT_to, diameter_ZT_from, diameter_ZT_to, length_L_ZT_from, length_L_ZT_to, cr_steg_from, cr_steg_to, cr_niere_from, cr_niere_to, ca_from, ca_to, ct_from, ct_to, ck_from, ck_to, date)
values(1, 43, 43, 43, 43, 43, 43, 43, 43, 43, 43, 43, 43, 43, 43, 43, 43, 43, 43, 43, 43, 43, 43, 43, 43, date('2022-11-14'));

insert into multiexcel.queries(user_id, rubber_from, rubber_to, diameter_AT_from, diameter_AT_to, length_L_AT_from, length_L_AT_to, diameter_IT_from, diameter_IT_to, length_L_IT_from, length_L_IT_to, diameter_ZT_from, diameter_ZT_to, length_L_ZT_from, length_L_ZT_to, cr_steg_from, cr_steg_to, cr_niere_from, cr_niere_to, ca_from, ca_to, ct_from, ct_to, ck_from, ck_to, date)
values(2, 44, 44, 44, 44, 44, 44, 44, 44, 44, 44, 44, 44, 44, 44, 44, 44, 44, 44, 44, 44, 44, 44, 44, 44, date('2022-11-13'));

insert into multiexcel.queries(user_id, rubber_from, rubber_to, diameter_AT_from, diameter_AT_to, length_L_AT_from, length_L_AT_to, diameter_IT_from, diameter_IT_to, length_L_IT_from, length_L_IT_to, diameter_ZT_from, diameter_ZT_to, length_L_ZT_from, length_L_ZT_to, cr_steg_from, cr_steg_to, cr_niere_from, cr_niere_to, ca_from, ca_to, ct_from, ct_to, ck_from, ck_to, date)
values(1, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, date('2022-11-12'));

insert into multiexcel.queries(user_id, rubber_from, rubber_to, diameter_AT_from, diameter_AT_to, length_L_AT_from, length_L_AT_to, diameter_IT_from, diameter_IT_to, length_L_IT_from, length_L_IT_to, diameter_ZT_from, diameter_ZT_to, length_L_ZT_from, length_L_ZT_to, cr_steg_from, cr_steg_to, cr_niere_from, cr_niere_to, ca_from, ca_to, ct_from, ct_to, ck_from, ck_to, date)
values(2, 46, 46, 46, 46, 46, 46, 46, 46, 46, 46, 46, 46, 46, 46, 46, 46, 46, 46, 46, 46, 46, 46, 46, 46, date('2022-11-11'));

insert into multiexcel.queries(user_id, rubber_from, rubber_to, diameter_AT_from, diameter_AT_to, length_L_AT_from, length_L_AT_to, diameter_IT_from, diameter_IT_to, length_L_IT_from, length_L_IT_to, diameter_ZT_from, diameter_ZT_to, length_L_ZT_from, length_L_ZT_to, cr_steg_from, cr_steg_to, cr_niere_from, cr_niere_to, ca_from, ca_to, ct_from, ct_to, ck_from, ck_to, date)
values(1, 47, 47, 47, 47, 47, 47, 47, 47, 47, 47, 47, 47, 47, 47, 47, 47, 47, 47, 47, 47, 47, 47, 47, 47, date('2022-11-10'));

insert into multiexcel.queries(user_id, rubber_from, rubber_to, diameter_AT_from, diameter_AT_to, length_L_AT_from, length_L_AT_to, diameter_IT_from, diameter_IT_to, length_L_IT_from, length_L_IT_to, diameter_ZT_from, diameter_ZT_to, length_L_ZT_from, length_L_ZT_to, cr_steg_from, cr_steg_to, cr_niere_from, cr_niere_to, ca_from, ca_to, ct_from, ct_to, ck_from, ck_to, date)
values(2, 48, 48, 48, 48, 48, 48, 48, 48, 48, 48, 48, 48, 48, 48, 48, 48, 48, 48, 48, 48, 48, 48, 48, 48, date('2022-11-09'));

insert into multiexcel.queries(user_id, rubber_from, rubber_to, diameter_AT_from, diameter_AT_to, length_L_AT_from, length_L_AT_to, diameter_IT_from, diameter_IT_to, length_L_IT_from, length_L_IT_to, diameter_ZT_from, diameter_ZT_to, length_L_ZT_from, length_L_ZT_to, cr_steg_from, cr_steg_to, cr_niere_from, cr_niere_to, ca_from, ca_to, ct_from, ct_to, ck_from, ck_to, date)
values(1, 49, 49, 49, 49, 49, 49, 49, 49, 49, 49, 49, 49, 49, 49, 49, 49, 49, 49, 49, 49, 49, 49, 49, 49, date('2022-11-08'));

insert into multiexcel.queries(user_id, rubber_from, rubber_to, diameter_AT_from, diameter_AT_to, length_L_AT_from, length_L_AT_to, diameter_IT_from, diameter_IT_to, length_L_IT_from, length_L_IT_to, diameter_ZT_from, diameter_ZT_to, length_L_ZT_from, length_L_ZT_to, cr_steg_from, cr_steg_to, cr_niere_from, cr_niere_to, ca_from, ca_to, ct_from, ct_to, ck_from, ck_to, date)
values(2, 50, 50, 50, 50, 50, 50, 50, 50, 50, 50, 50, 50, 50, 50, 50, 50, 50, 50, 50, 50, 50, 50, 50, 50, date('2022-11-07'));

insert into multiexcel.queries(user_id, rubber_from, rubber_to, diameter_AT_from, diameter_AT_to, length_L_AT_from, length_L_AT_to, diameter_IT_from, diameter_IT_to, length_L_IT_from, length_L_IT_to, diameter_ZT_from, diameter_ZT_to, length_L_ZT_from, length_L_ZT_to, cr_steg_from, cr_steg_to, cr_niere_from, cr_niere_to, ca_from, ca_to, ct_from, ct_to, ck_from, ck_to, date)
values(1, 51, 51, 51, 51, 51, 51, 51, 51, 51, 51, 51, 51, 51, 51, 51, 51, 51, 51, 51, 51, 51, 51, 51, 51, date('2022-11-06'));

insert into multiexcel.queries(user_id, rubber_from, rubber_to, diameter_AT_from, diameter_AT_to, length_L_AT_from, length_L_AT_to, diameter_IT_from, diameter_IT_to, length_L_IT_from, length_L_IT_to, diameter_ZT_from, diameter_ZT_to, length_L_ZT_from, length_L_ZT_to, cr_steg_from, cr_steg_to, cr_niere_from, cr_niere_to, ca_from, ca_to, ct_from, ct_to, ck_from, ck_to, date)
values(2, 52, 52, 52, 52, 52, 52, 52, 52, 52, 52, 52, 52, 52, 52, 52, 52, 52, 52, 52, 52, 52, 52, 52, 52, date('2022-11-05'));

insert into multiexcel.queries(user_id, rubber_from, rubber_to, diameter_AT_from, diameter_AT_to, length_L_AT_from, length_L_AT_to, diameter_IT_from, diameter_IT_to, length_L_IT_from, length_L_IT_to, diameter_ZT_from, diameter_ZT_to, length_L_ZT_from, length_L_ZT_to, cr_steg_from, cr_steg_to, cr_niere_from, cr_niere_to, ca_from, ca_to, ct_from, ct_to, ck_from, ck_to, date)
values(1, 53, 53, 53, 53, 53, 53, 53, 53, 53, 53, 53, 53, 53, 53, 53, 53, 53, 53, 53, 53, 53, 53, 53, 53, date('2022-11-04'));

insert into multiexcel.queries(user_id, rubber_from, rubber_to, diameter_AT_from, diameter_AT_to, length_L_AT_from, length_L_AT_to, diameter_IT_from, diameter_IT_to, length_L_IT_from, length_L_IT_to, diameter_ZT_from, diameter_ZT_to, length_L_ZT_from, length_L_ZT_to, cr_steg_from, cr_steg_to, cr_niere_from, cr_niere_to, ca_from, ca_to, ct_from, ct_to, ck_from, ck_to, date)
values(2, 54, 54, 54, 54, 54, 54, 54, 54, 54, 54, 54, 54, 54, 54, 54, 54, 54, 54, 54, 54, 54, 54, 54, 54, date('2022-11-03'));

insert into multiexcel.queries(user_id, rubber_from, rubber_to, diameter_AT_from, diameter_AT_to, length_L_AT_from, length_L_AT_to, diameter_IT_from, diameter_IT_to, length_L_IT_from, length_L_IT_to, diameter_ZT_from, diameter_ZT_to, length_L_ZT_from, length_L_ZT_to, cr_steg_from, cr_steg_to, cr_niere_from, cr_niere_to, ca_from, ca_to, ct_from, ct_to, ck_from, ck_to, date)
values(1, 55, 55, 55, 55, 55, 55, 55, 55, 55, 55, 55, 55, 55, 55, 55, 55, 55, 55, 55, 55, 55, 55, 55, 55, date('2022-11-02'));

insert into multiexcel.queries(user_id, rubber_from, rubber_to, diameter_AT_from, diameter_AT_to, length_L_AT_from, length_L_AT_to, diameter_IT_from, diameter_IT_to, length_L_IT_from, length_L_IT_to, diameter_ZT_from, diameter_ZT_to, length_L_ZT_from, length_L_ZT_to, cr_steg_from, cr_steg_to, cr_niere_from, cr_niere_to, ca_from, ca_to, ct_from, ct_to, ck_from, ck_to, date)
values(2, 56, 56, 56, 56, 56, 56, 56, 56, 56, 56, 56, 56, 56, 56, 56, 56, 56, 56, 56, 56, 56, 56, 56, 56, date('2022-11-01'));

insert into multiexcel.queries(user_id, rubber_from, rubber_to, diameter_AT_from, diameter_AT_to, length_L_AT_from, length_L_AT_to, diameter_IT_from, diameter_IT_to, length_L_IT_from, length_L_IT_to, diameter_ZT_from, diameter_ZT_to, length_L_ZT_from, length_L_ZT_to, cr_steg_from, cr_steg_to, cr_niere_from, cr_niere_to, ca_from, ca_to, ct_from, ct_to, ck_from, ck_to, date)
values(1, 57, 57, 57, 57, 57, 57, 57, 57, 57, 57, 57, 57, 57, 57, 57, 57, 57, 57, 57, 57, 57, 57, 57, 57, date('2022-10-31'));

insert into multiexcel.queries(user_id, rubber_from, rubber_to, diameter_AT_from, diameter_AT_to, length_L_AT_from, length_L_AT_to, diameter_IT_from, diameter_IT_to, length_L_IT_from, length_L_IT_to, diameter_ZT_from, diameter_ZT_to, length_L_ZT_from, length_L_ZT_to, cr_steg_from, cr_steg_to, cr_niere_from, cr_niere_to, ca_from, ca_to, ct_from, ct_to, ck_from, ck_to, date)
values(2, 58, 58, 58, 58, 58, 58, 58, 58, 58, 58, 58, 58, 58, 58, 58, 58, 58, 58, 58, 58, 58, 58, 58, 58, date('2022-10-30'));

insert into multiexcel.queries(user_id, rubber_from, rubber_to, diameter_AT_from, diameter_AT_to, length_L_AT_from, length_L_AT_to, diameter_IT_from, diameter_IT_to, length_L_IT_from, length_L_IT_to, diameter_ZT_from, diameter_ZT_to, length_L_ZT_from, length_L_ZT_to, cr_steg_from, cr_steg_to, cr_niere_from, cr_niere_to, ca_from, ca_to, ct_from, ct_to, ck_from, ck_to, date)
values(1, 59, 59, 59, 59, 59, 59, 59, 59, 59, 59, 59, 59, 59, 59, 59, 59, 59, 59, 59, 59, 59, 59, 59, 59, date('2022-10-29'));

insert into multiexcel.queries(user_id, rubber_from, rubber_to, diameter_AT_from, diameter_AT_to, length_L_AT_from, length_L_AT_to, diameter_IT_from, diameter_IT_to, length_L_IT_from, length_L_IT_to, diameter_ZT_from, diameter_ZT_to, length_L_ZT_from, length_L_ZT_to, cr_steg_from, cr_steg_to, cr_niere_from, cr_niere_to, ca_from, ca_to, ct_from, ct_to, ck_from, ck_to, date)
values(2, 60, 60, 60, 60, 60, 60, 60, 60, 60, 60, 60, 60, 60, 60, 60, 60, 60, 60, 60, 60, 60, 60, 60, 60, date('2022-10-28'));

INSERT INTO multiexcel.categories_queries(category_id, query_id)
values (1, 7);
INSERT INTO multiexcel.categories_queries(category_id, query_id)
values (2, 8);
INSERT INTO multiexcel.categories_queries(category_id, query_id)
values (3, 9);
INSERT INTO multiexcel.categories_queries(category_id, query_id)
values (4, 10);
INSERT INTO multiexcel.categories_queries(category_id, query_id)
values (5, 11);
INSERT INTO multiexcel.categories_queries(category_id, query_id)
values (6, 12);
INSERT INTO multiexcel.categories_queries(category_id, query_id)
values (7, 13);
INSERT INTO multiexcel.categories_queries(category_id, query_id)
values (8, 14);
INSERT INTO multiexcel.categories_queries(category_id, query_id)
values (9, 15);
INSERT INTO multiexcel.categories_queries(category_id, query_id)
values (1, 16);
INSERT INTO multiexcel.categories_queries(category_id, query_id)
values (2, 17);
INSERT INTO multiexcel.categories_queries(category_id, query_id)
values (3, 18);
INSERT INTO multiexcel.categories_queries(category_id, query_id)
values (4, 19);
INSERT INTO multiexcel.categories_queries(category_id, query_id)
values (5, 20);
INSERT INTO multiexcel.categories_queries(category_id, query_id)
values (6, 21);
INSERT INTO multiexcel.categories_queries(category_id, query_id)
values (7, 22);
INSERT INTO multiexcel.categories_queries(category_id, query_id)
values (8, 23);
INSERT INTO multiexcel.categories_queries(category_id, query_id)
values (9, 24);
INSERT INTO multiexcel.categories_queries(category_id, query_id)
values (1, 25);
INSERT INTO multiexcel.categories_queries(category_id, query_id)
values (2, 26);
INSERT INTO multiexcel.categories_queries(category_id, query_id)
values (3, 27);
INSERT INTO multiexcel.categories_queries(category_id, query_id)
values (4, 28);
INSERT INTO multiexcel.categories_queries(category_id, query_id)
values (5, 29);
INSERT INTO multiexcel.categories_queries(category_id, query_id)
values (6, 30);
INSERT INTO multiexcel.categories_queries(category_id, query_id)
values (7, 31);
INSERT INTO multiexcel.categories_queries(category_id, query_id)
values (8, 32);
INSERT INTO multiexcel.categories_queries(category_id, query_id)
values (9, 33);
INSERT INTO multiexcel.categories_queries(category_id, query_id)
values (1, 34);
INSERT INTO multiexcel.categories_queries(category_id, query_id)
values (2, 35);
INSERT INTO multiexcel.categories_queries(category_id, query_id)
values (3, 36);
INSERT INTO multiexcel.categories_queries(category_id, query_id)
values (4, 37);
INSERT INTO multiexcel.categories_queries(category_id, query_id)
values (5, 38);
INSERT INTO multiexcel.categories_queries(category_id, query_id)
values (6, 39);
INSERT INTO multiexcel.categories_queries(category_id, query_id)
values (7, 40);
INSERT INTO multiexcel.categories_queries(category_id, query_id)
values (8, 41);
INSERT INTO multiexcel.categories_queries(category_id, query_id)
values (9, 42);
INSERT INTO multiexcel.categories_queries(category_id, query_id)
values (1, 43);
INSERT INTO multiexcel.categories_queries(category_id, query_id)
values (2, 44);
INSERT INTO multiexcel.categories_queries(category_id, query_id)
values (3, 45);
INSERT INTO multiexcel.categories_queries(category_id, query_id)
values (4, 46);
INSERT INTO multiexcel.categories_queries(category_id, query_id)
values (5, 47);
INSERT INTO multiexcel.categories_queries(category_id, query_id)
values (6, 48);
INSERT INTO multiexcel.categories_queries(category_id, query_id)
values (7, 49);
INSERT INTO multiexcel.categories_queries(category_id, query_id)
values (8, 50);
INSERT INTO multiexcel.categories_queries(category_id, query_id)
values (9, 51);
INSERT INTO multiexcel.categories_queries(category_id, query_id)
values (1, 52);
INSERT INTO multiexcel.categories_queries(category_id, query_id)
values (2, 53);
INSERT INTO multiexcel.categories_queries(category_id, query_id)
values (3, 54);
INSERT INTO multiexcel.categories_queries(category_id, query_id)
values (4, 55);
INSERT INTO multiexcel.categories_queries(category_id, query_id)
values (5, 56);
INSERT INTO multiexcel.categories_queries(category_id, query_id)
values (6, 57);
INSERT INTO multiexcel.categories_queries(category_id, query_id)
values (7, 58);
INSERT INTO multiexcel.categories_queries(category_id, query_id)
values (8, 59);
INSERT INTO multiexcel.categories_queries(category_id, query_id)
values (9, 60);
INSERT INTO multiexcel.categories_queries(category_id, query_id)
values (1, 61);
INSERT INTO multiexcel.categories_queries(category_id, query_id)
values (2, 62);
INSERT INTO multiexcel.categories_queries(category_id, query_id)
values (3, 63);
INSERT INTO multiexcel.categories_queries(category_id, query_id)
values (4, 64);
INSERT INTO multiexcel.categories_queries(category_id, query_id)
values (5, 65);
INSERT INTO multiexcel.categories_queries(category_id, query_id)
values (6, 66);
