CREATE TABLE rentals (
    id SERIAL PRIMARY KEY,
    customer_id INT NOT NULL,
    reservation_id INT NOT NULL,
    is_active BOOLEAN NOT NULL DEFAULT true,
    is_deleted BOOLEAN NOT NULL DEFAULT false,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,
    CONSTRAINT rentals_customer_id_fkey FOREIGN KEY (customer_id)
      REFERENCES customers (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
    CONSTRAINT rentals_reservation_id_fkey FOREIGN KEY (reservation_id)
      REFERENCES reservations (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);