INSERT INTO users (user_id) VALUES (1);

-- Insert the base account for the test user
INSERT INTO accounts (user_id, account_name, account_balance) VALUES (1, 'Chequing', 0.00);

-- Insert categories. The database will automatically assign an ID for each.
INSERT INTO categories (category_name) VALUES
                                           ('Other'),
                                           ('Rent'),
                                           ('Groceries'),
                                           ('Shopping'),
                                           ('Transportation'),
                                           ('Restaurants');
