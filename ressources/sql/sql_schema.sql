CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    full_name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    birth_date VARCHAR(255) NOT NULL,
    validation_token VARCHAR(255),
    validated BOOLEAN DEFAULT FALSE
);