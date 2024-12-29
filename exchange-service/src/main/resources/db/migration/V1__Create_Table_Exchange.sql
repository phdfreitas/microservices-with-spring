CREATE SEQUENCE exchange_sequence START 1;

CREATE TABLE exchange (
    id BIGINT PRIMARY KEY DEFAULT nextval('exchange_sequence'),
    from_currency CHAR(3) NOT NULL,
    to_currency CHAR(3) NOT NULL,
    conversion_factor NUMERIC(19, 2) NOT NULL
);
