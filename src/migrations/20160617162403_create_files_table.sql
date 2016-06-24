CREATE TABLE files (
	id SERIAL PRIMARY KEY,
	display_name VARCHAR(255) NOT NULL,
	name VARCHAR(255) NOT NULL,
	mimetype VARCHAR(255) NOT NULL,
	size BIGINT NOT NULL,
	parent_id INT,
	parent_type VARCHAR(60),
	is_active BOOLEAN NOT NULL DEFAULT true,
	is_deleted BOOLEAN NOT NULL DEFAULT false,
	created_at TIMESTAMP NOT NULL,
	updated_at TIMESTAMP NOT NULL
);
