CREATE TABLE customer_emails (
    id SERIAL PRIMARY KEY,
    customer_id INT NOT NULL,
    nickname VARCHAR(60) NOT NULL,
    address VARCHAR(80) NOT NULL,
    is_default BOOLEAN NOT NULL DEFAULT false,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,
    CONSTRAINT customer_emails_customer_id_fkey FOREIGN KEY (customer_id)
      REFERENCES customers (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);
