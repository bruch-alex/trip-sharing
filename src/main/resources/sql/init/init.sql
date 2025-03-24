CREATE TABLE IF NOT EXISTS users
(
    id           SERIAL PRIMARY KEY,
    email        TEXT NOT NULL UNIQUE, --TODO: add regex check
    phone_number TEXT NOT NULL UNIQUE, --TODO: add regex check
    password     TEXT NOT NULL,        --TODO: add regex check
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
