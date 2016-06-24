CREATE TABLE invoices (
    id SERIAL PRIMARY KEY,
    customer_id INT NOT NULL,
    description VARCHAR(255) NOT NULL,
	subtotal BIGINT NOT NULL,
	discount BIGINT NOT NULL,
	total BIGINT NOT NULL,
	paid BOOLEAN NOT NULL DEFAULT true,
    parent_id INT,
	parent_type VARCHAR(60),
    is_closed BOOLEAN NOT NULL DEFAULT false,
	is_active BOOLEAN NOT NULL DEFAULT true,
    is_deleted BOOLEAN NOT NULL DEFAULT false,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,
    CONSTRAINT invoices_customer_id_fkey FOREIGN KEY (customer_id)
      REFERENCES customers (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);