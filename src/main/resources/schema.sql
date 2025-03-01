DROP TABLE IF EXISTS RESERVATIONS;

CREATE TABLE RESERVATIONS (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(250) NOT NULL,
    phone_number VARCHAR(250) NOT NULL,
    email VARCHAR(250) NOT NULL,
    reservation_date TIMESTAMP NOT NULL,
    notification_type VARCHAR(250) NOT NULL,
    guests INT NOT NULL,
    status VARCHAR(250) NOT NULL
);