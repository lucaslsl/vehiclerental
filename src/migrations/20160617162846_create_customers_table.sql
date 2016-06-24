CREATE TABLE customers (
	id SERIAL PRIMARY KEY,
	first_name VARCHAR(30) NOT NULL,
	last_name VARCHAR(60) NOT NULL,
	sex INT NOT NULL DEFAULT 9,
	birthdate DATE NOT NULL,
	single BOOLEAN NOT NULL DEFAULT true,
	marital_status_description VARCHAR(255),
	parent_one_name VARCHAR(160),
	parent_two_name VARCHAR(160),
	nationality VARCHAR(60),
	birthplace VARCHAR(100),
	driving_license_number VARCHAR(100),
	driving_license_expiry_date DATE,
	is_active BOOLEAN NOT NULL DEFAULT true,
	is_deleted BOOLEAN NOT NULL DEFAULT false,
	created_at TIMESTAMP NOT NULL,
	updated_at TIMESTAMP NOT NULL
);
