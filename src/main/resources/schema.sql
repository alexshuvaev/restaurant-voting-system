DROP TABLE IF EXISTS user_role;
DROP TABLE IF EXISTS vote;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS dish;
DROP TABLE IF EXISTS restaurant;

CREATE TABLE users
(
    id         INTEGER                 NOT NULL AUTO_INCREMENT,
    name       VARCHAR(255)            NOT NULL,
    email      VARCHAR(255)            NOT NULL,
    password   VARCHAR(255)            NOT NULL,
    registered TIMESTAMP DEFAULT now() NOT NULL,
    enabled    BOOLEAN   DEFAULT TRUE  NOT NULL,
    PRIMARY KEY (id)
);
CREATE UNIQUE INDEX users_unique_email_idx ON users (email);

CREATE TABLE user_role
(
    role    VARCHAR(255) NOT NULL,
    user_id INTEGER      NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);

CREATE TABLE restaurant
(
    id        INTEGER      NOT NULL AUTO_INCREMENT,
    name      VARCHAR(255) NOT NULL,
    telephone VARCHAR(255) NOT NULL,
    address   VARCHAR(255) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE dish
(
    id            INTEGER            NOT NULL AUTO_INCREMENT,
    name          VARCHAR(255)       NOT NULL,
    price         DECIMAL            NOT NULL,
    date          DATE DEFAULT now() NOT NULL,
    restaurant_id INTEGER            NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (restaurant_id) REFERENCES restaurant (id) ON DELETE CASCADE
);
CREATE INDEX dish_unique_date_idx ON dish (date);

CREATE TABLE vote
(
    id                      INTEGER                     NOT NULL AUTO_INCREMENT,
    date                    DATE DEFAULT current_date() NOT NULL,
    time                    TIME DEFAULT current_time() NOT NULL,
    user_id                 INTEGER,
    user_email_history      VARCHAR(255)                NOT NULL,
    restaurant_id           INTEGER,
    restaurant_name_history VARCHAR(255)                NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE SET NULL,
    FOREIGN KEY (restaurant_id) REFERENCES restaurant (id) ON DELETE SET NULL
);
CREATE INDEX vote_uniq_date_time_idx ON vote (date, time);
CREATE INDEX vote_uniq_user_idx ON vote (user_email_history);
CREATE INDEX vote_uniq_restaurant_idx ON vote (restaurant_name_history);