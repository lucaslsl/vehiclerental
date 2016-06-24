CREATE TABLE accidents (
    id SERIAL PRIMARY KEY,
    customer_id INT NOT NULL,
    rental_id INT NOT NULL,
    occurrence_date TIMESTAMP NOT NULL,
    is_active BOOLEAN NOT NULL DEFAULT true,
    is_deleted BOOLEAN NOT NULL DEFAULT false,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,
    CONSTRAINT accidents_customer_id_fkey FOREIGN KEY (customer_id)
      REFERENCES customers (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
    CONSTRAINT accidents_rental_id_fkey FOREIGN KEY (rental_id)
      REFERENCES rentals (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);