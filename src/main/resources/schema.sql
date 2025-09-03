DROP TABLE IF EXISTS transactions;
DROP TABLE IF EXISTS user_accounts;
DROP TABLE IF EXISTS categories;
DROP TABLE IF EXISTS debts;
DROP TABLE IF EXISTS accounts;
DROP TABLE IF EXISTS users;

-- Create the `users` table.
CREATE TABLE users (
    user_id INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(255) NOT NULL UNIQUE,
    email VARCHAR(255) NOT NULL UNIQUE
);

-- Create the `accounts` table.
CREATE TABLE accounts (
    account_id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT NOT NULL,
    account_name VARCHAR(255) NOT NULL,
    account_balance DECIMAL(10, 2) NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE
);

-- Create the `categories` table.
CREATE TABLE categories (
    category_id INT PRIMARY KEY AUTO_INCREMENT,
    category_name VARCHAR(255) NOT NULL UNIQUE
);

-- Create the `debts` table.
CREATE TABLE debts (
    debt_id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT NOT NULL,
    debt_name VARCHAR(255) NOT NULL,
    amount DECIMAL(10, 2) NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE
);

-- Finally, create the `transactions` table after all its dependencies are in place.
CREATE TABLE transactions (
    transaction_id INT PRIMARY KEY AUTO_INCREMENT,
    account_id INT NOT NULL,
    user_id INT NOT NULL,
    amount DECIMAL(10, 2) NOT NULL,
    description VARCHAR(255),
    category_id INT,
    debt_id INT,
    transaction_date DATE NOT NULL,
    notes VARCHAR(255),
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE,
    FOREIGN KEY (account_id) REFERENCES accounts(account_id) ON DELETE CASCADE,
    FOREIGN KEY (category_id) REFERENCES categories(category_id) ON DELETE SET NULL,
    FOREIGN KEY (debt_id) REFERENCES debts(debt_id) ON DELETE SET NULL
);
