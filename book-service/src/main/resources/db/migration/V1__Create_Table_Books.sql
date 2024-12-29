CREATE SEQUENCE book_sequence START 1;

CREATE TABLE book (
    id BIGINT PRIMARY KEY DEFAULT nextval('book_sequence'),
    author TEXT,
    launch_date TIMESTAMP NOT NULL,
    price NUMERIC(65, 2) NOT NULL,
    title TEXT
);
