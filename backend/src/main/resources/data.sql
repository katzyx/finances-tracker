-- Use INSERT OR IGNORE to prevent errors on subsequent runs
INSERT OR IGNORE INTO users (user_id) VALUES (1);

-- Insert the base account for the test user
INSERT OR IGNORE INTO accounts (user_id, account_name, account_balance) VALUES (1, 'Chequing', 0.00);

-- Insert categories
INSERT OR IGNORE INTO categories (category_name) VALUES ('Other');
INSERT OR IGNORE INTO categories (category_name) VALUES ('Rent');
INSERT OR IGNORE INTO categories (category_name) VALUES ('Groceries');
INSERT OR IGNORE INTO categories (category_name) VALUES ('Shopping');
INSERT OR IGNORE INTO categories (category_name) VALUES ('Transportation');
INSERT OR IGNORE INTO categories (category_name) VALUES ('Restaurants');