CREATE SEQUENCE IF NOT EXISTS global_sequence START WITH 100000;

CREATE TABLE IF NOT EXISTS users
(
    id          INT PRIMARY KEY NOT NULL,
    created_at  TIMESTAMP       NOT NULL,
    modified_at TIMESTAMP,
    version     INT             NOT NULL,
    username    VARCHAR(255)    NOT NULL,
    password    VARCHAR(255)    NOT NULL
);

CREATE UNIQUE INDEX user_username_unique_idx ON users (username);

CREATE TABLE IF NOT EXISTS user_role
(
    user_id INT REFERENCES users (id),
    role    VARCHAR(255),
    CONSTRAINT uk_user_role UNIQUE (user_id, role),
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS request
(
    id          INT PRIMARY KEY NOT NULL,
    created_at  TIMESTAMP       NOT NULL,
    modified_at TIMESTAMP,
    version     INT             NOT NULL,
    title       VARCHAR(255)    NOT NULL,
    description TEXT            NOT NULL,
    status      VARCHAR(255)    NOT NULL,
    user_id     INT             NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
)