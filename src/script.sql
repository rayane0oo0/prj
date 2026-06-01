CREATE DATABASE IF NOT EXISTS gestion_produits;
USE gestion_produits;

CREATE TABLE IF NOT EXISTS produit (
    id INT AUTO_INCREMENT PRIMARY KEY,
    code VARCHAR(50) NOT NULL UNIQUE,
    libelle VARCHAR(100) NOT NULL,
    type VARCHAR(50) NOT NULL,
    quantite_stock INT NOT NULL,
    disponibilite BOOLEAN NOT NULL
);



