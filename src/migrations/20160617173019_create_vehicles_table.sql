CREATE TABLE vehicles (
    id SERIAL PRIMARY KEY,
    vehicle_class_id INT NOT NULL,
    vehicle_brand_id INT NOT NULL,
    name VARCHAR(30) NOT NULL,
    production_year INT NOT NULL,
    vin VARCHAR(30) NOT NULL UNIQUE,
    is_active BOOLEAN NOT NULL DEFAULT true,
    is_deleted BOOLEAN NOT NULL DEFAULT false,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,
    CONSTRAINT vehicles_vehicle_class_id_fkey FOREIGN KEY (vehicle_class_id)
      REFERENCES vehicle_classes (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
    CONSTRAINT vehicles_vehicle_brand_id_fkey FOREIGN KEY (vehicle_brand_id)
      REFERENCES vehicle_brands (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);
