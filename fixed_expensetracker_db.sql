-- Drop database only if it exists
DROP DATABASE IF EXISTS expensetrackerdb;

-- Drop user only if it exists
DO $$ BEGIN
    IF EXISTS (SELECT FROM pg_roles WHERE rolname = 'expensetracker') THEN
        DROP USER expensetracker;
END IF;
END $$;

-- Create user
CREATE USER expensetracker WITH PASSWORD 'password';

-- Create database with correct owner
CREATE DATABASE expensetrackerdb WITH TEMPLATE template0 OWNER expensetracker;

-- Connect to the new database
\c expensetrackerdb

-- Grant privileges to the user
ALTER DEFAULT PRIVILEGES GRANT ALL ON TABLES TO expensetracker;
ALTER DEFAULT PRIVILEGES GRANT ALL ON SEQUENCES TO expensetracker;

-- Create tables
CREATE TABLE et_users (
                          user_id SERIAL PRIMARY KEY,
                          first_name VARCHAR(20) NOT NULL,
                          last_name VARCHAR(20) NOT NULL,
                          email VARCHAR(30) NOT NULL UNIQUE,
                          password TEXT NOT NULL
);

CREATE TABLE et_categories (
                               category_id SERIAL PRIMARY KEY,
                               user_id INTEGER NOT NULL,
                               title VARCHAR(20) NOT NULL,
                               description VARCHAR(50) NOT NULL,
                               CONSTRAINT cat_users_fk FOREIGN KEY (user_id) REFERENCES et_users (user_id)
);

CREATE TABLE et_transactions (
                                 transaction_id SERIAL PRIMARY KEY,
                                 category_id INTEGER NOT NULL,
                                 user_id INTEGER NOT NULL,
                                 amount NUMERIC(10, 2) NOT NULL,
                                 note VARCHAR(50) NOT NULL,
                                 transaction_date BIGINT NOT NULL,
                                 CONSTRAINT trans_cat_fk FOREIGN KEY (category_id) REFERENCES et_categories (category_id),
                                 CONSTRAINT trans_users_fk FOREIGN KEY (user_id) REFERENCES et_users (user_id)
);

-- Create sequences
CREATE SEQUENCE et_users_seq INCREMENT 1 START 1;
CREATE SEQUENCE et_categories_seq INCREMENT 1 START 1;
CREATE SEQUENCE et_transactions_seq INCREMENT 1 START 1000;