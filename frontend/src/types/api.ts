export interface User {
  userId: number;
}

export interface Account {
  accountId: number;
  userId: User;
  accountName: string;
  accountBalance: number;
}

export interface Category {
  categoryId: number;
  categoryName: string;
}

export interface Debt {
  debtId: number;
  userId: User;
  debtName: string;
  totalOwed: number;
  amountPaid: number;
  monthlyPayment: number;
  remainingBalance: number;
  paymentProgress: number;
}

export interface Transaction {
  transactionId: number;
  accountId: Account;
  userId: User;
  amount: number;
  description: string;
  categoryId: Category;
  debtId?: Debt;
  transactionDate: string;
  type: 'income' | 'expense';
  recurrence?: 'weekly' | 'monthly' | 'yearly';
}

export interface CreateAccountDTO {
  userId: number;
  accountName: string;
  accountBalance: number;
}

export interface CreateTransactionDTO {
  accountId: number;
  userId: number;
  amount: number;
  description: string;
  categoryId: number;
  debtId?: number;
  type: 'income' | 'expense';
  recurrence?: 'weekly' | 'monthly' | 'yearly';
}

export interface CreateDebtDTO {
  userId: number;
  debtName: string;
  totalOwed: number;
  amountPaid?: number;
  monthlyPayment: number;
}

export interface PaymentRequest {
  paymentAmount: number;
}