CREATE TABLE customer_documents (
    id SERIAL PRIMARY KEY,
    customer_id INT NOT NULL,
    name VARCHAR(60) NOT NULL,
    number VARCHAR(40) NOT NULL,
    authority VARCHAR(80),
    issue_date DATE NOT NULL,
    observations VARCHAR(255),
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,
    CONSTRAINT customer_documents_customer_id_fkey FOREIGN KEY (customer_id)
      REFERENCES customers (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);