-- liquibase formatted sql

-- changeset user:1728422876816-15
insert into role (id, name)
values (1, 'Пользователь');

-- changeset user:1728422876816-16
insert into role (id, name)
values (2, 'Оператор');

-- changeset user:1728422876816-17
insert into role (id, name)
values (3, 'Администратор');

-- changeset user:1728422876816-18
insert into "user" (id, username , password, active)
values (1, 'Пользователь', 'Пользователь', TRUE);
-- changeset user:1728422876816-19
insert into "user" (id, username , password, active)
values (2, 'Оператор', 'Оператор', TRUE);
-- changeset user:1728422876816-20
insert into "user" (id, username , password, active)
values (3, 'Администратор', 'Администратор', TRUE);


-- changeset user:1728422876816-21
insert into user_roles (user_id, roles_id) values (1, 1);
-- changeset user:1728422876816-22
insert into user_roles (user_id, roles_id) values (2, 2);
-- changeset user:1728422876816-23
insert into user_roles (user_id, roles_id) values (3, 3);