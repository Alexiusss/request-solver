DELETE FROM request;

INSERT INTO request (id, created_at, version, title, description, status, user_id)
VALUES (11, now(), 0, 'First request title', 'First request description', 'DRAFT', 3),
       (12, now(), 1, 'Second request title', 'Second request description', 'SENT', 3),
       (13, now(), 2, 'Third request title', 'Third request description', 'ACCEPTED', 3),
       (14, now(), 3, 'Fourth request title', 'Fourth request description', 'REJECTED', 3);
