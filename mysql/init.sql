CREATE DATABASE IF NOT EXISTS market;

USE market;

CREATE TABLE IF NOT EXISTS products (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100),
    price DECIMAL(10,2)
);

INSERT INTO products (name, price) VALUES
('Apple', 0.99),
('Banana', 1.10),
('Orange', 0.85);
