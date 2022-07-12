INSERT INTO role(id, role_name) values (1, 'ADMIN'), (2, 'USER');
INSERT INTO usuario(username, password, email, enabled) values ('Bolters', '$2a$10$TYOgUggV.8IrIla/qp9aCOj6LW2..GCSdwTUkOOX9Atil//6Tf4HS','bolters@gmail.com', 'true');
INSERT INTO usuario(username, password, email, enabled) values ('Mario', '$2a$10$TYOgUggV.8IrIla/qp9aCOj6LW2..GCSdwTUkOOX9Atil//6Tf4HS','mario@gmail.com', 'true');
INSERT INTO usuario_roles(usuario_id, roles_id) values (1, 1), (1, 2), (2, 2);
