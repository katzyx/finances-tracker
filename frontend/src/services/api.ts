import type { 
  User, 
  Account, 
  Category, 
  Debt, 
  Transaction, 
  CreateAccountDTO, 
  CreateTransactionDTO, 
  CreateDebtDTO, 
  PaymentRequest 
} from '@/types/api';

const API_BASE = 'http://localhost:8080';

class ApiService {
  private async request<T>(endpoint: string, options?: RequestInit): Promise<T> {
    const response = await fetch(`${API_BASE}${endpoint}`, {
      headers: {
        'Content-Type': 'application/json',
        ...options?.headers,
      },
      ...options,
    });

    if (!response.ok) {
      throw new Error(`API Error: ${response.statusText}`);
    }

    return response.json();
  }

  // User APIs
  async getUser(): Promise<User> {
    return this.request<User>('/users/1');
  }

  // Account APIs
  async getAccounts(): Promise<Account[]> {
    return this.request<Account[]>('/accounts');
  }

  async getAccountById(id: number): Promise<Account> {
    return this.request<Account>(`/accounts/${id}`);
  }

  async getAccountsByUserId(userId: number): Promise<Account[]> {
    return this.request<Account[]>(`/accounts/user/${userId}`);
  }

  async createAccount(account: CreateAccountDTO): Promise<Account> {
    return this.request<Account>('/accounts', {
      method: 'POST',
      body: JSON.stringify(account),
    });
  }

  async updateAccount(id: number, account: Account): Promise<Account> {
    return this.request<Account>(`/accounts/${id}`, {
      method: 'PUT',
      body: JSON.stringify(account),
    });
  }

  async deleteAccount(id: number): Promise<void> {
    await this.request<void>(`/accounts/${id}`, {
      method: 'DELETE',
    });
  }

  // Transaction APIs
  async getTransactions(): Promise<Transaction[]> {
    return this.request<Transaction[]>('/transactions');
  }

  async getTransactionById(id: number): Promise<Transaction> {
    return this.request<Transaction>(`/transactions/${id}`);
  }

  async getTransactionsByUserId(userId: number): Promise<Transaction[]> {
    return this.request<Transaction[]>(`/transactions/user/${userId}`);
  }

  async getTransactionsByAccountId(accountId: number): Promise<Transaction[]> {
    return this.request<Transaction[]>(`/transactions/account/${accountId}`);
  }

  async getTransactionsByCategoryId(categoryId: number): Promise<Transaction[]> {
    return this.request<Transaction[]>(`/transactions/category/${categoryId}`);
  }

  async createTransaction(transaction: CreateTransactionDTO): Promise<Transaction> {
    return this.request<Transaction>('/transactions', {
      method: 'POST',
      body: JSON.stringify(transaction),
    });
  }

  async updateTransaction(id: number, transaction: Transaction): Promise<Transaction> {
    return this.request<Transaction>(`/transactions/${id}`, {
      method: 'PUT',
      body: JSON.stringify(transaction),
    });
  }

  async deleteTransaction(id: number): Promise<void> {
    await this.request<void>(`/transactions/${id}`, {
      method: 'DELETE',
    });
  }

  // Category APIs
  async getCategories(): Promise<Category[]> {
    return this.request<Category[]>('/categories');
  }

  async getCategoryById(id: number): Promise<Category> {
    return this.request<Category>(`/categories/${id}`);
  }

  async createCategory(category: { categoryName: string }): Promise<Category> {
    return this.request<Category>('/categories', {
      method: 'POST',
      body: JSON.stringify(category),
    });
  }

  async updateCategory(id: number, category: Category): Promise<Category> {
    return this.request<Category>(`/categories/${id}`, {
      method: 'PUT',
      body: JSON.stringify(category),
    });
  }

  async deleteCategory(id: number): Promise<void> {
    await this.request<void>(`/categories/${id}`, {
      method: 'DELETE',
    });
  }

  // Debt APIs
  async getDebts(): Promise<Debt[]> {
    return this.request<Debt[]>('/debts');
  }

  async getDebtById(id: number): Promise<Debt> {
    return this.request<Debt>(`/debts/${id}`);
  }

  async getDebtsByUserId(userId: number): Promise<Debt[]> {
    return this.request<Debt[]>(`/debts/user/${userId}`);
  }

  async getActiveDebtsByUserId(userId: number): Promise<Debt[]> {
    return this.request<Debt[]>(`/debts/user/${userId}/active`);
  }

  async getPaidOffDebtsByUserId(userId: number): Promise<Debt[]> {
    return this.request<Debt[]>(`/debts/user/${userId}/paid-off`);
  }

  async getTotalRemainingDebt(userId: number): Promise<number> {
    return this.request<number>(`/debts/user/${userId}/total-remaining`);
  }

  async createDebt(debt: CreateDebtDTO): Promise<Debt> {
    return this.request<Debt>('/debts', {
      method: 'POST',
      body: JSON.stringify(debt),
    });
  }

  async updateDebt(id: number, debt: Debt): Promise<Debt> {
    return this.request<Debt>(`/debts/${id}`, {
      method: 'PUT',
      body: JSON.stringify(debt),
    });
  }

  async makeDebtPayment(id: number, payment: PaymentRequest): Promise<Debt> {
    return this.request<Debt>(`/debts/${id}/payment`, {
      method: 'POST',
      body: JSON.stringify(payment),
    });
  }

  async deleteDebt(id: number): Promise<void> {
    await this.request<void>(`/debts/${id}`, {
      method: 'DELETE',
    });
  }
}

export const apiService = new ApiService();