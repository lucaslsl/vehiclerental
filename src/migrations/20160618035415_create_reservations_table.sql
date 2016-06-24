CREATE TABLE reservations (
    id SERIAL PRIMARY KEY,
    vehicle_id INT NOT NULL,
    customer_id INT NOT NULL,
    start_date DATE NOT NULL,
    end_date DATE NOT NULL,
    remarks VARCHAR(500),
    is_active BOOLEAN NOT NULL DEFAULT true,
    is_deleted BOOLEAN NOT NULL DEFAULT false,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,
    CONSTRAINT reservations_vehicle_id_fkey FOREIGN KEY (vehicle_id)
      REFERENCES vehicles (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
    CONSTRAINT reservations_customer_id_fkey FOREIGN KEY (customer_id)
      REFERENCES customers (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);