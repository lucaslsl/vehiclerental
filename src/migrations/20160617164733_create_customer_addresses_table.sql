CREATE TABLE customer_addresses (
    id SERIAL PRIMARY KEY,
    customer_id INT NOT NULL,
    nickname VARCHAR(60) NOT NULL,
    formatted_address VARCHAR(160) NOT NULL,
    locality VARCHAR(60) NOT NULL,
    country VARCHAR(60),
    postal_code VARCHAR(60),
    google_place_id VARCHAR(50),
    is_default BOOLEAN NOT NULL DEFAULT false,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,
    CONSTRAINT customer_addresses_customer_id_fkey FOREIGN KEY (customer_id)
      REFERENCES customers (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);
