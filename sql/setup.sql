-- CREATE DATABASE skill_marketplace
-- USE skill_marketplace;
-- DROP TABLE users,skills,service_requests,accounts;
SELECT * FROM users;

 CREATE TABLE IF NOT EXISTS users(
 	user_id INT PRIMARY KEY AUTO_INCREMENT,
     name VARCHAR(100) NOT NULL,
     phone VARCHAR(20) NOT NULL,
     area VARCHAR(100) NOT NULL,
     created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
 );

 CREATE TABLE skills(
 skill_id INT PRIMARY KEY AUTO_INCREMENT,
 user_id INT  NOT NULL,
 skill_name VARCHAR(100) NOT NULL,
 category VARCHAR(50) NOT NULL,
 description TEXT,
 price_per_hour DECIMAL(10,2),
 is_available BOOLEAN,
 FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE
 );


 CREATE TABLE service_requests(
 	request_id INT PRIMARY KEY AUTO_INCREMENT,
     posted_by INT  NOT NULL,
     skill_needed VARCHAR(100) NOT NULL,
     description TEXT,
     status ENUM('open','fulfilled','cancelled') DEFAULT 'open',
     posted_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
     FOREIGN KEY (posted_by) REFERENCES users(user_id) ON DELETE CASCADE
);



CREATE TABLE accounts (
    account_id INT AUTO_INCREMENT PRIMARY KEY,
    full_name VARCHAR(100) NOT NULL,
    username VARCHAR(50) UNIQUE NOT NULL,
    phone VARCHAR(20) NOT NULL,
    password VARCHAR(100) NOT NULL
);