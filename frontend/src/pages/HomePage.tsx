import React, { useState, useEffect } from 'react';
import { PageHeader } from '@/components/layout/PageHeader';
import { NetWorthCard } from '@/components/dashboard/NetWorthCard';
import { AccountsList } from '@/components/dashboard/AccountsList';
import { DebtsSummary } from '@/components/dashboard/DebtsSummary';
import { SpendingChart } from '@/components/charts/SpendingChart';
import { type Account, type Debt, type Transaction } from '@/types/api';
import { apiService } from '@/services/api';

export function HomePage() {
  const [accounts, setAccounts] = useState<Account[]>([]);
  const [debts, setDebts] = useState<Debt[]>([]);
  const [transactions, setTransactions] = useState<Transaction[]>([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const loadData = async () => {
      try {
        const [accountsRes, debtsRes, transactionsRes] = await Promise.all([
          apiService.getAccountsByUserId(1),
          apiService.getActiveDebtsByUserId(1),
          apiService.getTransactionsByUserId(1),
        ]);
        setAccounts(accountsRes);
        setDebts(debtsRes);
        setTransactions(transactionsRes);
      } catch (error) {
        console.error('Error loading dashboard data:', error);
      } finally {
        setLoading(false);
      }
    };

    loadData();
  }, []);

  if (loading) {
    return <div className="flex items-center justify-center h-64">Loading...</div>;
  }

  const totalAssets = accounts.reduce((sum, account) => sum + account.accountBalance, 0);
  const totalDebts = debts.reduce((sum, debt) => sum + debt.remainingBalance, 0);

  // Get spending data for current month
  const currentMonth = new Date().getMonth();
  const currentYear = new Date().getFullYear();
  const monthlyExpenses = transactions.filter(t => {
    const transactionDate = new Date(t.transactionDate);
    return t.type === 'expense' && 
           transactionDate.getMonth() === currentMonth && 
           transactionDate.getFullYear() === currentYear;
  });

  // Group expenses by category
  const spendingByCategory = monthlyExpenses.reduce((acc, transaction) => {
    const category = transaction.categoryId.categoryName;
    acc[category] = (acc[category] || 0) + transaction.amount;
    return acc;
  }, {} as Record<string, number>);

  const spendingData = Object.entries(spendingByCategory).map(([category, amount], index) => ({
    category,
    amount,
    color: ['#0088FE', '#00C49F', '#FFBB28', '#FF8042', '#8884d8', '#82ca9d'][index % 6]
  }));

  return (
    <div>
      <PageHeader 
        title="Dashboard" 
        subtitle="Your financial overview at a glance"
      />
      
      <div className="grid grid-cols-1 lg:grid-cols-3 gap-6 mb-8">
        <NetWorthCard 
          assets={totalAssets} 
          debts={totalDebts} 
        />
        <AccountsList 
          accounts={accounts} 
        />
        <DebtsSummary 
          debts={debts} 
        />
      </div>

      {spendingData.length > 0 && (
        <div className="grid grid-cols-1 lg:grid-cols-2 gap-6">
          <SpendingChart 
            data={spendingData} 
            title="This Month's Spending"
          />
        </div>
      )}
    </div>
  );
}