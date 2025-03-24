DROP SCHEMA public CASCADE;
CREATE SCHEMA public;

CREATE TABLE IF NOT EXISTS users
(
    id           SERIAL PRIMARY KEY,
    email        TEXT NOT NULL UNIQUE CHECK (email ~ '^[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,}$'),
    phone_number TEXT NOT NULL UNIQUE, --TODO: add regex check
    password     TEXT NOT NULL,        --TODO: add length check
    first_name   TEXT NOT NULL CHECK (LENGTH(first_name) >= 2),
    middle_name  TEXT CHECK (LENGTH(middle_name) >= 2),
    last_name    TEXT NOT NULL CHECK (LENGTH(last_name) >= 2),
    created_at   TIMESTAMP DEFAULT NOW(),
    updated_at   TIMESTAMP
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
    ON users
    FOR EACH ROW
EXECUTE FUNCTION update_modified_column();