INSERT INTO users(id, created_at, version, username, password)
VALUES (1, now(), 0, 'admin', '{noop}adminpassword'),
       (2, now(), 0, 'operator', '{noop}operatorpassword'),
       (3, now(), 0, 'user', '{noop}userpassword');

INSERT INTO user_role(user_id, role)
VALUES (1, 'ADMIN'),
       (2, 'OPERATOR'),
       (3, 'USER');