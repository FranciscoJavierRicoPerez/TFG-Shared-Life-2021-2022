-- INICIO DE LOS ROLES
INSERT INTO role (id, role_name) VALUES (1, 'ROLE_ADMIN');
INSERT INTO role (id, role_name) VALUES (2, 'ROLE_USER');

--- INICIO DE LOS USUARIOS
insert into users (id, email, first_name, last_name, password, username) values (1, 'francisco@gmail.com', 'Francisco', 'Rico Perez', '$2a$10$d1pe1FdXTO0L/f6Kw9imVeqeNx2sNLodEQJDDP4LGOqEOR90Ia/.W', 'Francisco');
insert into user_role (user_id, role_id) values (1, 1);
insert into user_role (user_id, role_id) values (1, 2);
insert into users (id, email, first_name, last_name, password, username) values (2, 'nereida@gmail.com', 'Nereida', 'Francisco Conejero', '$2a$10$CoiXamWbT8/WVeVPPrEYEO3pCyUigaLTTIN.WlB6K0zVPRXwslT2a', 'Nereida');
insert into user_role (user_id, role_id) values (2, 2);
insert into users (id, email, first_name, last_name, password, username) values (3, 'ainhoa@gmail.com', 'Ainhoa', 'Amoros Perez', '$2a$10$iKEhNUcXc0Ph4nS0mwtuAuj1e0P6U1dFKnhNZdsmQ7rYMI2YSzA5q', 'Ainhoa');
insert into user_role (user_id, role_id) values (3, 2);
insert into users (id, email, first_name, last_name, password, username) values (4, 'dylan@gmail.com', 'Dylan', 'Dylan', '$2a$10$eF8gs6qNGnPxtOAHqC/BIeUKFSUwaca5YJUyhnvuNb3n1jd5dq58q', 'Dylan');
insert into user_role (user_id, role_id) values (4, 2);
insert into users (id, email, first_name, last_name, password, username) values (5, 'eira@gmail.com', 'Eira', 'Eira', '$2a$10$WztWArEnbOxG6BWS6C3FjutmkNDhWVBo9akhyKuQPd3h7mdbEkQ/q', 'Eira');
insert into user_role (user_id, role_id) values (5, 2);