DROP SCHEMA public CASCADE;
CREATE SCHEMA public;

CREATE TABLE IF NOT EXISTS user_logins
(
    id           SERIAL PRIMARY KEY,
    email        TEXT NOT NULL UNIQUE,   --TODO: enable this later CHECK (email ~ '^[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,}$'),
    password     TEXT NOT NULL,          --TODO: add length check
    enabled      BOOLEAN   DEFAULT TRUE, --TODO: set default to false and send email to activate account
    created_at   TIMESTAMP DEFAULT NOW(),
    updated_at   TIMESTAMP
);

CREATE TABLE IF NOT EXISTS user_profiles
(
    id          INTEGER PRIMARY KEY,
    first_name  TEXT NOT NULL CHECK (LENGTH(first_name) >= 2),
    middle_name TEXT CHECK (LENGTH(middle_name) >= 2),
    last_name   TEXT NOT NULL CHECK (LENGTH(last_name) >= 2),
    FOREIGN KEY (id) REFERENCES user_logins (id)
);

CREATE TABLE IF NOT EXISTS roles
(
    id   SERIAL PRIMARY KEY,
    name TEXT NOT NULL
);

CREATE TABLE IF NOT EXISTS user_logins_roles
(
    user_login_id INT NOT NULL,
    role_id       INT NOT NULL,
    FOREIGN KEY (user_login_id) REFERENCES user_logins (id),
    FOREIGN KEY (role_id) REFERENCES roles (id)
);

CREATE OR REPLACE FUNCTION update_modified_column()
    RETURNS TRIGGER AS
$$
BEGIN
    new.updated_at = NOW();
    RETURN new;
END;
$$ LANGUAGE 'plpgsql';

CREATE TRIGGER update_modified_time
    BEFORE UPDATE
    ON user_logins
    FOR EACH ROW
EXECUTE FUNCTION update_modified_column();

CREATE TABLE IF NOT EXISTS vehicles
(
    id        SERIAL PRIMARY KEY,
    driver_id INT NOT NULL,
    FOREIGN KEY (driver_id) REFERENCES user_logins (id)
);

CREATE TABLE IF NOT EXISTS trips
(
    id                     SERIAL PRIMARY KEY,
    driver_id              INT       NOT NULL,
    vehicle_id             INT       NOT NULL,
    scheduled_start_time   TIMESTAMP NOT NULL,
    scheduled_arrival_time TIMESTAMP NOT NULL,
    actual_start_time      TIMESTAMP,
    actual_arrival_time    TIMESTAMP,
    FOREIGN KEY (driver_id) REFERENCES user_logins (id),
    FOREIGN KEY (vehicle_id) REFERENCES vehicles (id)
);

CREATE TABLE IF NOT EXISTS trip_passenger
(
    trip_id      INT NOT NULL,
    passenger_id INT NOT NULL,
    FOREIGN KEY (trip_id) REFERENCES trips (id),
    FOREIGN KEY (passenger_id) REFERENCES user_logins (id),
    PRIMARY KEY (trip_id, passenger_id)
);

INSERT INTO roles(name)
VALUES ('ROLE_USER'),
       ('ROLE_ADMIN');