# Personal Finance Tracker

A comprehensive, all-in-one, full-stack financial management application built with React, TypeScript, and Spring Boot. Track accounts, transactions, debts, and visualize your financial health through an intuitive dashboard interface with a RESTful API backend.

![Finance Tracker Dashboard](https://img.shields.io/badge/Status-Active-success)
![TypeScript](https://img.shields.io/badge/TypeScript-007ACC?logo=typescript&logoColor=white)
![React](https://img.shields.io/badge/React-61DAFB?logo=react&logoColor=black)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-6DB33F?logo=springboot&logoColor=white)
![SQLite](https://img.shields.io/badge/SQLite-003B57?logo=sqlite&logoColor=white)

## 🌟 Features

### 📊 Dashboard Overview
- **Real-time Net Worth Calculation**: Track total assets minus debts
- **Spending Visualization**: Interactive pie charts showing expense breakdown by category
- **Quick Account Summary**: View all account balances at a glance
- **Debt Progress Tracking**: Monitor debt payoff progress with visual indicators

### 💳 Account Management
- Create and manage multiple financial accounts (checking, savings, credit cards, investments)
- **Account Growth Charts**: Visualize balance changes over time based on transaction history
- **Monthly Growth Metrics**: Track month-over-month account performance with percentage changes
- **Transfer Between Accounts**: Seamlessly move money between accounts with automatic transaction creation
- Real-time balance updates with transaction integration

### 💰 Transaction Tracking
- Record income and expenses with detailed categorization
- **Advanced Filtering**: Filter by month, category, and transaction type
- **Monthly Overview**: Track total income, expenses, and net income
- Support for recurring transactions (weekly, monthly, yearly)
- Automatic account balance updates
- Quick-add transaction forms with category selection

### 🏦 Debt Management
- Track multiple debts (loans, credit cards, mortgages)
- **Visual Progress Bars**: See payment progress for each debt
- **Payment Processing**: Make payments that automatically update remaining balance
- Calculate and display overall debt progress
- Separate views for active and paid-off debts
- Monthly payment tracking and totals

### 📁 Category Organization
- Create custom categories for better expense tracking
- Category-based spending analysis
- Used across all transactions for consistent organization

## 🛠️ Technology Stack

### Frontend
- **React 18** with TypeScript for type-safe component development
- **React Router** for seamless navigation
- **Recharts** for interactive data visualizations
- **Tailwind CSS** for modern, responsive styling
- **Lucide React** for consistent iconography
- **shadcn/ui** components for polished UI elements
- **Vite** for fast development and optimized builds

### Backend
- **Spring Boot 3.x** for RESTful API development
- **Jakarta Persistence (JPA)** with Hibernate for ORM
- **SQLite** lightweight embedded database
- **Spring Data JPA** for simplified data access
- **Bean Validation** for DTO validation
- **CORS** configuration for frontend integration

### Architecture Patterns
- **DTO Pattern**: Separation of API contracts from domain models
- **Repository Pattern**: Data access abstraction
- **Service Layer**: Business logic encapsulation
- **RESTful Design**: Standard HTTP methods and status codes

## 📂 Project Structure

```
.
├── frontend/
│   └── src/
│       ├── components/
│       │   ├── charts/           # Data visualization components
│       │   ├── dashboard/        # Dashboard widgets
│       │   ├── forms/           # Form components for data entry
│       │   ├── layout/          # Layout components
│       │   └── ui/              # Reusable UI components
│       ├── pages/               # Main application pages
│       ├── services/
│       │   └── api.ts          # API service layer
│       ├── types/
│       │   └── api.ts          # TypeScript type definitions
│       └── lib/
│           └── utils.ts        # Utility functions
│
└── backend/
    └── src/main/java/com/example/finances/
        ├── controller/          # REST endpoints
        │   ├── AccountController.java
        │   ├── TransactionController.java
        │   ├── DebtController.java
        │   ├── CategoryController.java
        │   └── UserController.java
        ├── service/             # Business logic layer
        │   ├── AccountService.java
        │   ├── TransactionService.java
        │   ├── DebtService.java
        │   ├── CategoryService.java
        │   └── UserService.java
        ├── repository/          # Data access layer
        │   ├── AccountRepository.java
        │   ├── TransactionRepository.java
        │   ├── DebtRepository.java
        │   ├── CategoryRepository.java
        │   └── UserRepository.java
        ├── model/               # JPA entities
        │   ├── Account.java
        │   ├── Transaction.java
        │   ├── Debt.java
        │   ├── Category.java
        │   └── User.java
        ├── dto/                 # Data Transfer Objects
        │   ├── CreateAccountDTO.java
        │   ├── CreateTransactionDTO.java
        │   ├── CreateDebtDTO.java
        │   └── TransactionResponseDTO.java
        └── config/
            └── DataInitializer.java  # Database initialization
```

## 🔄 API Endpoints

### Accounts
- `GET /accounts` - Get all accounts
- `GET /accounts/{id}` - Get account by ID
- `GET /accounts/user/{userId}` - Get accounts by user
- `POST /accounts` - Create new account
- `PUT /accounts/{id}` - Update account
- `DELETE /accounts/{id}` - Delete account

### Transactions
- `GET /transactions` - Get all transactions
- `GET /transactions/{id}` - Get transaction by ID
- `GET /transactions/user/{userId}` - Get user's transactions
- `GET /transactions/account/{accountId}` - Get account's transactions
- `GET /transactions/category/{categoryId}` - Get transactions by category
- `POST /transactions` - Create new transaction
- `PUT /transactions/{id}` - Update transaction
- `DELETE /transactions/{id}` - Delete transaction

### Debts
- `GET /debts` - Get all debts
- `GET /debts/{id}` - Get debt by ID
- `GET /debts/user/{userId}` - Get user's debts
- `GET /debts/user/{userId}/active` - Get active debts
- `GET /debts/user/{userId}/paid-off` - Get paid-off debts
- `GET /debts/user/{userId}/total-remaining` - Get total remaining debt
- `POST /debts` - Create new debt
- `POST /debts/{id}/payment` - Make payment on debt
- `PUT /debts/{id}` - Update debt
- `DELETE /debts/{id}` - Delete debt

### Categories
- `GET /categories` - Get all categories
- `GET /categories/{id}` - Get category by ID
- `POST /categories` - Create new category
- `PUT /categories/{id}` - Update category
- `DELETE /categories/{id}` - Delete category

## 💻 Getting Started

### Prerequisites
- Node.js 18+ and npm
- Java 17+
- Maven 3.6+

### Backend Setup

1. **Navigate to backend directory**
```bash
cd backend
```

2. **Build the project**
```bash
mvn clean install
```

3. **Run the application**
```bash
mvn spring-boot:run
```

The API will be available at `http://localhost:8080`

**Database Initialization**: On first run, the application automatically creates:
- SQLite database file (`finances.db`)
- Initial schema with all tables
- Default user (ID: 1)
- Sample categories (Other, Rent, Groceries, Shopping, Transportation, Restaurants)
- Default chequing account

### Frontend Setup

1. **Navigate to frontend directory**
```bash
cd frontend
```

2. **Install dependencies**
```bash
npm install
```

3. **Start development server**
```bash
npm run dev
```

4. **Build for production**
```bash
npm run build
```

The application will be available at `http://localhost:5173`

## 🔄 Data Flow

### Creating a Transaction
1. **Frontend**: User fills out transaction form with amount, description, category
2. **API Call**: `POST /transactions` with `CreateTransactionDTO`
3. **Validation**: Spring validates DTO with Bean Validation annotations
4. **Service Layer**: 
   - Fetches related entities (Account, User, Category, optional Debt)
   - Creates Transaction entity
   - Calculates and updates account balance
5. **Repository**: Persists transaction to SQLite database
6. **Response**: Returns saved transaction with all relationships populated
7. **Frontend Update**: Refreshes transaction list and updates charts

### Account Balance Calculation
Balances are calculated in real-time based on transaction history:
- **Income transactions**: Add to account balance
- **Expense transactions**: Subtract from account balance
- **Transfers**: Create paired transactions (expense in source, income in destination)

## 🎨 Design Decisions

### Backend Architecture

#### DTO Pattern
Separates API contracts from domain models to:
- Prevent over-fetching of lazy-loaded relationships
- Provide clean API interfaces
- Enable validation at the API boundary
- Avoid circular serialization issues

```java
public class CreateTransactionDTO {
    @NotNull(message = "Amount is mandatory")
    @DecimalMin(value = "0.01", message = "Amount must be positive")
    private Double amount;
    
    @NotBlank(message = "Description is mandatory")
    private String description;
    
    @Pattern(regexp = "^(income|expense)$")
    private String type;
}
```

#### Repository Pattern
Spring Data JPA repositories provide:
- Automatic CRUD operations
- Custom query methods with naming conventions
- Complex queries with `@Query` annotations
- Type-safe data access

```java
public interface DebtRepository extends JpaRepository<Debt, Integer> {
    Optional<List<Debt>> findByUserId(User userId);
    
    @Query("SELECT d FROM Debt d WHERE d.userId = :user AND d.amountPaid < d.totalOwed")
    List<Debt> findActiveDebtsByUser(@Param("user") User userId);
}
```

#### Service Layer
Encapsulates business logic:
- Transaction management
- Validation beyond basic constraints
- Calculated fields (debt progress, remaining balance)
- Cross-entity operations (transfers, debt payments)

### Frontend Architecture

#### Component Hierarchy
- **Pages**: Route-level components managing data fetching and state
- **Forms**: Reusable form components with validation
- **UI Components**: Atomic, styled components from shadcn/ui
- **Charts**: Recharts integration for data visualization

#### State Management
Uses React hooks for local state:
- `useState` for component state
- `useEffect` for data fetching on mount
- No global state library needed for current scope

#### Type Safety
Full TypeScript coverage:
- API types match backend DTOs
- Type-safe API service layer
- Compile-time error detection

### Database Design

#### SQLite Choice
Lightweight embedded database ideal for:
- Single-user applications
- No separate database server needed
- File-based storage
- Zero configuration

#### Schema Design
```sql
-- Users table (single user in this version)
CREATE TABLE users (
    user_id INTEGER PRIMARY KEY AUTOINCREMENT
);

-- Accounts with cascade delete
CREATE TABLE accounts (
    account_id INTEGER PRIMARY KEY AUTOINCREMENT,
    user_id INTEGER NOT NULL,
    account_name TEXT NOT NULL,
    account_balance REAL NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE
);

-- Transactions with multiple relationships
CREATE TABLE transactions (
    transaction_id INTEGER PRIMARY KEY AUTOINCREMENT,
    account_id INTEGER NOT NULL,
    user_id INTEGER NOT NULL,
    category_id INTEGER,
    debt_id INTEGER,
    amount REAL NOT NULL,
    description TEXT NOT NULL,
    transaction_date DATE NOT NULL,
    type TEXT NOT NULL CHECK (type IN ('income', 'expense')),
    recurrence TEXT CHECK (recurrence IN ('weekly', 'monthly', 'yearly')),
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE,
    FOREIGN KEY (account_id) REFERENCES accounts(account_id) ON DELETE CASCADE,
    FOREIGN KEY (category_id) REFERENCES categories(category_id) ON DELETE SET NULL,
    FOREIGN KEY (debt_id) REFERENCES debts(debt_id) ON DELETE SET NULL
);
```

## 🎯 Key Implementation Details

### Account Growth Visualization
Historical balances calculated by:
1. Starting with current balance
2. Working backwards through transactions to find initial balance
3. Building forward timeline showing balance after each transaction
4. Displaying last 12 data points in line chart

### Debt Payment Processing
Payment flow:
1. Validate payment amount > 0
2. Check payment doesn't exceed remaining balance
3. Update `amountPaid` field
4. Automatically calculate `remainingBalance` and `paymentProgress`
5. Return updated debt with calculated fields

### Transfer Between Accounts
Transfer creates two transactions:
1. **Expense** in source account with description "Transfer to [destination]"
2. **Income** in destination account with description "Transfer from [source]"
3. Both use "Other" category by default
4. Maintains audit trail of money movement

### Data Initialization
`DataInitializer` component runs on startup:
- Checks if data exists (`userRepository.count()`)
- Creates default user if none exists
- Sets up initial categories
- Creates default chequing account
- Uses `@PostConstruct` for automatic execution

## 📈 Future Enhancements

- [ ] Budget planning and forecasting with AI suggestions
- [ ] Bill reminders and push notifications
- [ ] Multi-currency support with exchange rates
- [ ] Data export (CSV, PDF reports)
- [ ] Investment portfolio tracking with real-time quotes
- [ ] Goal setting with progress tracking
- [ ] Multi-user support with authentication
- [ ] Mobile responsive improvements
- [ ] Dark mode theme
- [ ] Advanced reporting and analytics dashboard
- [ ] Scheduled/recurring transaction automation
- [ ] Bank account integration (Plaid API)

## 🧪 Testing

### Backend Tests
```bash
cd backend
mvn test
```

### Frontend Tests
```bash
cd frontend
npm test
```

## 🤝 Contributing

This is a personal project, but suggestions and feedback are welcome!

## 📝 License

This project is for educational and portfolio purposes.

---

Built with modern full-stack technologies: React + TypeScript + Spring Boot + SQLite
