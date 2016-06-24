CREATE TABLE insurances (
	id SERIAL PRIMARY KEY,
	name VARCHAR(60) NOT NULL,
	description VARCHAR(255),
	is_active BOOLEAN NOT NULL DEFAULT true,
	is_deleted BOOLEAN NOT NULL DEFAULT false,
	created_at TIMESTAMP NOT NULL,
	updated_at TIMESTAMP NOT NULL
);