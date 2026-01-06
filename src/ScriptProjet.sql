-- Mysql 9.5.0 Client command
-- 1. Création de la base de données
CREATE DATABASE IF NOT EXISTS emsi_db;
USE emsi_db;

-- 2. Table des employés (Table parente)
CREATE TABLE IF NOT EXISTS employees (
                                         id INT PRIMARY KEY,
                                         nom VARCHAR(100) NOT NULL,
    prenom VARCHAR(100) NOT NULL,
    poste VARCHAR(100),
    salaire DOUBLE DEFAULT 0.0
    );

-- 3. Table des utilisateurs (Sécurité & Authentification)
CREATE TABLE IF NOT EXISTS users (
                                     id INT AUTO_INCREMENT PRIMARY KEY,
                                     username VARCHAR(50) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    role ENUM('ADMIN', 'EMPLOYEE') NOT NULL,
    employee_id INT,
    FOREIGN KEY (employee_id) REFERENCES employees(id) ON DELETE CASCADE
    );

-- 4. Table des tâches (Messagerie)
CREATE TABLE IF NOT EXISTS taches (
                                      id INT AUTO_INCREMENT PRIMARY KEY,
                                      employee_id INT NOT NULL,
                                      description TEXT NOT NULL,
                                      date_saisie TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                      FOREIGN KEY (employee_id) REFERENCES employees(id) ON DELETE CASCADE
    );

-- ---------------------------------------------------------
-- 5. INSERTION DES DONNÉES INITIALES (JEU DE TEST)
-- ---------------------------------------------------------

-- Insertion de l'employé Admin (Obligatoire pour la clé étrangère)
INSERT INTO employees (id, nom, prenom, poste, salaire)
VALUES (1, 'ADMIN', 'System', 'Administrateur', 0.0);

-- Insertion du compte Admin (Password: admin)
-- Le hash ci-dessous correspond au mot "admin" avec BCrypt
INSERT INTO users (username, password_hash, role, employee_id)
VALUES ('admin', '$2a$10$8.UnVuG9HHgffUDAlk8qfOuVGkqRzgVymGe07xd00DMp9G9Rk3Uga', 'ADMIN', 1);

-- Insertion d'un employé de test pour vérifier l'ID 12
INSERT INTO employees (id, nom, prenom, poste, salaire)
VALUES (12, 'TALIB', 'Abdo', 'Ingénieur Logiciel', 25000.0);