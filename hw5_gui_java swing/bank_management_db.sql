DROP DATABASE IF EXISTS bank_account;
CREATE DATABASE bank_account;
USE bank_account;

CREATE TABLE Account (
    account_number VARCHAR(100) PRIMARY KEY,
    balance DECIMAL(12, 2),
    type ENUM('Saving', 'Checking')
);