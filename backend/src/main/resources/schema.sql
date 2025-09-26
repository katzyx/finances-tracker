-- SQLite schema for finances application
PRAGMA foreign_keys = ON;

DROP TABLE IF EXISTS transactions;
DROP TABLE IF EXISTS user_accounts;
DROP TABLE IF EXISTS categories;
DROP TABLE IF EXISTS debts;
DROP TABLE IF EXISTS accounts;
DROP TABLE IF EXISTS users;

-- Create the `users` table.
CREATE TABLE users (
    user_id INTEGER PRIMARY KEY AUTOINCREMENT
);

-- Create the `accounts` table.
CREATE TABLE accounts (
    account_id INTEGER PRIMARY KEY AUTOINCREMENT,
    user_id INTEGER NOT NULL,
    account_name TEXT NOT NULL,
    account_balance REAL NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE
);

-- Create the `categories` table.
CREATE TABLE categories (
    category_id INTEGER PRIMARY KEY AUTOINCREMENT,
    category_name TEXT NOT NULL UNIQUE
);

-- Create the `debts` table.
CREATE TABLE debts (
    debt_id INTEGER PRIMARY KEY AUTOINCREMENT,
    user_id INTEGER NOT NULL,
    debt_name TEXT NOT NULL,
    total_owed REAL NOT NULL,
    amount_paid REAL NOT NULL,
    monthly_payment REAL NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE
);

CREATE TABLE transactions (
    transaction_id INTEGER PRIMARY KEY AUTOINCREMENT,
    account_id INTEGER NOT NULL,
    user_id INTEGER NOT NULL,
    amount REAL NOT NULL,
    description TEXT NOT NULL,
    category_id INTEGER,
    debt_id INTEGER,
    transaction_date DATE NOT NULL,
    type TEXT NOT NULL CHECK (type IN ('income', 'expense')),
    recurrence TEXT CHECK (recurrence IN ('weekly', 'monthly', 'yearly') OR recurrence IS NULL),
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE,
    FOREIGN KEY (account_id) REFERENCES accounts(account_id) ON DELETE CASCADE,
    FOREIGN KEY (category_id) REFERENCES categories(category_id) ON DELETE SET NULL,
    FOREIGN KEY (debt_id) REFERENCES debts(debt_id) ON DELETE SET NULL
);