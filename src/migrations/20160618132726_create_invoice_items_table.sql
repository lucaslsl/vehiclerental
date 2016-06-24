CREATE TABLE invoice_items (
	id SERIAL PRIMARY KEY,
	invoice_id INT NOT NULL,
	name VARCHAR(60) NOT NULL,
	description VARCHAR(255),
	cost BIGINT NOT NULL,
	created_at TIMESTAMP NOT NULL,
	updated_at TIMESTAMP NOT NULL,
	CONSTRAINT invoice_items_invoice_id_fkey FOREIGN KEY (invoice_id)
      REFERENCES invoices (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);