-- Create database
CREATE DATABASE IF NOT EXISTS agriculture_db;
USE agriculture_db;

-- Create users table
CREATE TABLE users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    phone VARCHAR(20),
    farm_size DECIMAL(10,2),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Create crops table
CREATE TABLE crops (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    type VARCHAR(50) NOT NULL,
    planting_date DATE,
    harvest_date DATE,
    area DECIMAL(10,2),
    status ENUM('PLANTED', 'GROWING', 'HARVESTED', 'FAILED') DEFAULT 'PLANTED',
    user_id BIGINT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

-- Create irrigation table
CREATE TABLE irrigation (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    crop_id BIGINT,
    scheduled_date DATETIME NOT NULL,
    duration_minutes INT NOT NULL,
    water_amount_liters DECIMAL(10,2),
    status ENUM('SCHEDULED', 'COMPLETED', 'CANCELLED') DEFAULT 'SCHEDULED',
    user_id BIGINT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (crop_id) REFERENCES crops(id) ON DELETE SET NULL,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

-- Insert sample data
INSERT INTO users (first_name, last_name, email, password, phone, farm_size) VALUES
('John', 'Doe', 'john.doe@example.com', '$2a$10$example.hash', '+1234567890', 50.5),
('Jane', 'Smith', 'jane.smith@example.com', '$2a$10$example.hash', '+0987654321', 75.0);

INSERT INTO crops (name, type, planting_date, harvest_date, area, status, user_id) VALUES
('Wheat Crop 1', 'WHEAT', '2024-01-15', '2024-06-15', 25.0, 'GROWING', 1),
('Rice Crop 1', 'RICE', '2024-02-01', '2024-08-01', 30.0, 'PLANTED', 1),
('Corn Crop 1', 'CORN', '2024-03-01', '2024-09-01', 20.0, 'GROWING', 2);

INSERT INTO irrigation (crop_id, scheduled_date, duration_minutes, water_amount_liters, status, user_id) VALUES
(1, '2024-03-15 08:00:00', 30, 150.0, 'SCHEDULED', 1),
(2, '2024-03-16 07:30:00', 45, 200.0, 'SCHEDULED', 1),
(3, '2024-03-17 09:00:00', 25, 120.0, 'COMPLETED', 2);