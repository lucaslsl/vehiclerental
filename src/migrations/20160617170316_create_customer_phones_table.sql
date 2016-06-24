CREATE TABLE customer_phones (
    id SERIAL PRIMARY KEY,
    customer_id INT NOT NULL,
    nickname VARCHAR(60) NOT NULL,
    country_code VARCHAR(10) NOT NULL,
    area_code VARCHAR(10) NOT NULL,
    number VARCHAR(30) NOT NULL,
    is_default BOOLEAN NOT NULL DEFAULT false,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,
    CONSTRAINT customer_phones_customer_id_fkey FOREIGN KEY (customer_id)
      REFERENCES customers (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);
