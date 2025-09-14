DROP TABLE IF EXISTS transactions;
DROP TABLE IF EXISTS user_accounts;
DROP TABLE IF EXISTS categories;
DROP TABLE IF EXISTS debts;
DROP TABLE IF EXISTS accounts;
DROP TABLE IF EXISTS users;

-- Create the `users` table.
CREATE TABLE users (
    user_id INT PRIMARY KEY AUTO_INCREMENT
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
    total_owed DECIMAL(10, 2) NOT NULL,
    amount_paid DECIMAL(10, 2) NOT NULL,
    monthly_payment DECIMAL(10, 2) NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE
);

CREATE TABLE transactions (
    transaction_id INT PRIMARY KEY AUTO_INCREMENT,
    account_id INT NOT NULL,
    user_id INT NOT NULL,
    amount DECIMAL(10, 2) NOT NULL,
    description VARCHAR(255) NOT NULL,
    category_id INT,
    debt_id INT,
    transaction_date DATE NOT NULL,
    type ENUM('income', 'expense') NOT NULL,
    recurrence ENUM('weekly', 'monthly', 'yearly') NULL,
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE,
    FOREIGN KEY (account_id) REFERENCES accounts(account_id) ON DELETE CASCADE,
    FOREIGN KEY (category_id) REFERENCES categories(category_id) ON DELETE SET NULL,
    FOREIGN KEY (debt_id) REFERENCES debts(debt_id) ON DELETE SET NULL
);
