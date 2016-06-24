CREATE TABLE additional_informations (
    id SERIAL PRIMARY KEY,
    key VARCHAR(60) NOT NULL,
    value VARCHAR(100) NOT NULL,
    parent_id INT,
	parent_type VARCHAR(60),
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL
);