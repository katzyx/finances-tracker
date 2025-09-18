import React, { useState, useEffect } from 'react';
import { Plus, Edit, Trash2, ArrowRightLeft, TrendingUp } from 'lucide-react';
import { PageHeader } from '@/components/layout/PageHeader';
import { Button } from '@/components/ui/button';
import { Card, CardContent, CardHeader, CardTitle } from '@/components/ui/card';
import { Table, TableBody, TableCell, TableHead, TableHeader, TableRow } from '@/components/ui/table';
import { Input } from '@/components/ui/input';
import { Label } from '@/components/ui/label';
import { Select } from '@/components/ui/select';
import { AccountForm } from '@/components/forms/AccountForm';
import { AccountGrowthChart } from '@/components/charts/AccountGrowthChart';
import { type Account, type CreateAccountDTO, type Transaction } from '@/types/api';
import { apiService } from '@/services/api';
import { formatCurrency, formatDate } from '@/lib/utils';

export function AccountsPage() {
  const [accounts, setAccounts] = useState<Account[]>([]);
  const [transactions, setTransactions] = useState<Transaction[]>([]);
  const [loading, setLoading] = useState(true);
  const [showForm, setShowForm] = useState(false);
  const [showTransferModal, setShowTransferModal] = useState(false);
  const [editingAccount, setEditingAccount] = useState<Account | null>(null);
  const [selectedAccount, setSelectedAccount] = useState<Account | null>(null);
  
  // Transfer state
  const [transferData, setTransferData] = useState({
    fromAccountId: 0,
    toAccountId: 0,
    amount: 0,
    description: 'Transfer between accounts'
  });

  useEffect(() => {
    loadData();
  }, []);

  const loadData = async () => {
    try {
      const [accountsRes, transactionsRes] = await Promise.all([
        apiService.getAccountsByUserId(1),
        apiService.getTransactionsByUserId(1),
      ]);
      setAccounts(accountsRes);
      setTransactions(transactionsRes);
    } catch (error) {
      console.error('Error loading data:', error);
    } finally {
      setLoading(false);
    }
  };

  const handleCreateAccount = async (accountData: CreateAccountDTO) => {
    try {
      await apiService.createAccount(accountData);
      await loadData();
      setShowForm(false);
    } catch (error) {
      console.error('Error creating account:', error);
    }
  };

  const handleUpdateAccount = async (accountData: CreateAccountDTO) => {
    if (!editingAccount) return;
    
    try {
      const updatedAccount: Account = {
        ...editingAccount,
        accountName: accountData.accountName,
        accountBalance: accountData.accountBalance,
      };
      
      await apiService.updateAccount(editingAccount.accountId, updatedAccount);
      await loadData();
      setEditingAccount(null);
    } catch (error) {
      console.error('Error updating account:', error);
    }
  };

  const handleDeleteAccount = async (id: number) => {
    if (window.confirm('Are you sure you want to delete this account? This will also delete all associated transactions.')) {
      try {
        await apiService.deleteAccount(id);
        await loadData();
      } catch (error) {
        console.error('Error deleting account:', error);
      }
    }
  };

  const handleTransfer = async () => {
    if (transferData.fromAccountId === transferData.toAccountId) {
      alert('Cannot transfer to the same account');
      return;
    }

    if (transferData.amount <= 0) {
      alert('Transfer amount must be greater than 0');
      return;
    }

    try {
      // Create expense transaction for source account
      await apiService.createTransaction({
        accountId: transferData.fromAccountId,
        userId: 1,
        amount: transferData.amount,
        description: `Transfer to ${accounts.find(a => a.accountId === transferData.toAccountId)?.accountName}`,
        categoryId: 1, // Assuming "Other" category has ID 1
        type: 'expense'
      });

      // Create income transaction for destination account
      await apiService.createTransaction({
        accountId: transferData.toAccountId,
        userId: 1,
        amount: transferData.amount,
        description: `Transfer from ${accounts.find(a => a.accountId === transferData.fromAccountId)?.accountName}`,
        categoryId: 1, // Assuming "Other" category has ID 1
        type: 'income'
      });

      await loadData();
      setShowTransferModal(false);
      setTransferData({
        fromAccountId: 0,
        toAccountId: 0,
        amount: 0,
        description: 'Transfer between accounts'
      });
    } catch (error) {
      console.error('Error processing transfer:', error);
      alert('Error processing transfer. Please try again.');
    }
  };

  const getAccountTransactions = (accountId: number) => {
    return transactions.filter(t => t.accountId.accountId === accountId);
  };

  const totalBalance = accounts.reduce((sum, account) => sum + account.accountBalance, 0);

  // Generate account growth data based on actual transactions
  const generateGrowthData = (account: Account) => {
    const accountTransactions = getAccountTransactions(account.accountId);
    const sortedTransactions = accountTransactions.sort((a, b) => 
      new Date(a.transactionDate).getTime() - new Date(b.transactionDate).getTime()
    );

    let runningBalance = account.accountBalance;
    const data = [];

    // Start with current balance and work backwards
    for (let i = sortedTransactions.length - 1; i >= 0; i--) {
      const transaction = sortedTransactions[i];
      if (transaction.type === 'income') {
        runningBalance -= transaction.amount;
      } else {
        runningBalance += transaction.amount;
      }
    }

    // Now build the growth data forward
    let currentBalance = runningBalance;
    for (const transaction of sortedTransactions) {
      if (transaction.type === 'income') {
        currentBalance += transaction.amount;
      } else {
        currentBalance -= transaction.amount;
      }
      
      data.push({
        date: formatDate(transaction.transactionDate),
        balance: currentBalance
      });
    }

    // Add current balance as the latest point
    data.push({
      date: formatDate(new Date()),
      balance: account.accountBalance
    });

    return data.slice(-12); // Show last 12 data points
  };

  if (loading) {
    return <div className="flex items-center justify-center h-64">Loading...</div>;
  }

  if (showForm || editingAccount) {
    return (
      <div>
        <PageHeader title="Accounts" />
        <AccountForm
          onSubmit={editingAccount ? handleUpdateAccount : handleCreateAccount}
          onCancel={() => {
            setShowForm(false);
            setEditingAccount(null);
          }}
          initialData={editingAccount ? {
            accountName: editingAccount.accountName,
            accountBalance: editingAccount.accountBalance,
          } : undefined}
        />
      </div>
    );
  }

  return (
    <div>
      <PageHeader
        title="Account Details"
        subtitle="Manage your financial accounts and view their growth"
      >
        <div className="flex space-x-2">
          <Button variant="outline" onClick={() => setShowTransferModal(true)}>
            <ArrowRightLeft className="w-4 h-4 mr-2" />
            Transfer
          </Button>
          <Button onClick={() => setShowForm(true)}>
            <Plus className="w-4 h-4 mr-2" />
            Add Account
          </Button>
        </div>
      </PageHeader>

      {/* Summary Cards */}
      <div className="grid grid-cols-1 lg:grid-cols-3 gap-6 mb-8">
        <Card>
          <CardContent className="p-6">
            <div className="flex items-center justify-between">
              <div>
                <p className="text-sm text-muted-foreground">Total Balance</p>
                <p className="text-2xl font-bold text-green-600">{formatCurrency(totalBalance)}</p>
              </div>
              <TrendingUp className="w-8 h-8 text-green-600" />
            </div>
          </CardContent>
        </Card>

        <Card>
          <CardContent className="p-6">
            <div className="flex items-center justify-between">
              <div>
                <p className="text-sm text-muted-foreground">Total Accounts</p>
                <p className="text-2xl font-bold">{accounts.length}</p>
              </div>
            </div>
          </CardContent>
        </Card>

        <Card>
          <CardContent className="p-6">
            <div className="flex items-center justify-between">
              <div>
                <p className="text-sm text-muted-foreground">Average Balance</p>
                <p className="text-2xl font-bold">
                  {formatCurrency(accounts.length > 0 ? totalBalance / accounts.length : 0)}
                </p>
              </div>
            </div>
          </CardContent>
        </Card>
      </div>

      {/* Account Growth Chart */}
      {selectedAccount && (
        <div className="mb-8">
          <AccountGrowthChart 
            data={generateGrowthData(selectedAccount)} 
            title={`${selectedAccount.accountName} Growth`}
          />
        </div>
      )}

      {/* Transfer Modal */}
      {showTransferModal && (
        <div className="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center z-50">
          <Card className="w-full max-w-md mx-4">
            <CardHeader>
              <CardTitle>Transfer Between Accounts</CardTitle>
            </CardHeader>
            <CardContent className="space-y-4">
              <div>
                <Label htmlFor="fromAccount">From Account</Label>
                <Select
                  id="fromAccount"
                  value={transferData.fromAccountId}
                  onChange={(e) => setTransferData(prev => ({
                    ...prev,
                    fromAccountId: parseInt(e.target.value)
                  }))}
                  required
                >
                  <option value={0}>Select Account</option>
                  {accounts.map((account) => (
                    <option key={account.accountId} value={account.accountId}>
                      {account.accountName} ({formatCurrency(account.accountBalance)})
                    </option>
                  ))}
                </Select>
              </div>

              <div>
                <Label htmlFor="toAccount">To Account</Label>
                <Select
                  id="toAccount"
                  value={transferData.toAccountId}
                  onChange={(e) => setTransferData(prev => ({
                    ...prev,
                    toAccountId: parseInt(e.target.value)
                  }))}
                  required
                >
                  <option value={0}>Select Account</option>
                  {accounts.map((account) => (
                    <option key={account.accountId} value={account.accountId}>
                      {account.accountName} ({formatCurrency(account.accountBalance)})
                    </option>
                  ))}
                </Select>
              </div>

              <div>
                <Label htmlFor="amount">Amount</Label>
                <Input
                  id="amount"
                  type="number"
                  step="0.01"
                  min="0.01"
                  value={transferData.amount}
                  onChange={(e) => setTransferData(prev => ({
                    ...prev,
                    amount: parseFloat(e.target.value)
                  }))}
                  placeholder="0.00"
                  required
                />
              </div>

              <div className="flex justify-end space-x-2 pt-4">
                <Button variant="outline" onClick={() => setShowTransferModal(false)}>
                  Cancel
                </Button>
                <Button onClick={handleTransfer}>
                  Transfer
                </Button>
              </div>
            </CardContent>
          </Card>
        </div>
      )}

      {/* Accounts Table */}
      <Card className="mb-8">
        <CardHeader>
          <CardTitle>Your Accounts</CardTitle>
        </CardHeader>
        <CardContent>
          <Table>
            <TableHeader>
              <TableRow>
                <TableHead>Account Name</TableHead>
                <TableHead className="text-right">Balance</TableHead>
                <TableHead className="text-right">Transactions</TableHead>
                <TableHead className="text-right">Actions</TableHead>
              </TableRow>
            </TableHeader>
            <TableBody>
              {accounts.map((account) => {
                const accountTransactions = getAccountTransactions(account.accountId);
                return (
                  <TableRow key={account.accountId}>
                    <TableCell className="font-medium">
                      {account.accountName}
                    </TableCell>
                    <TableCell className="text-right font-medium">
                      {formatCurrency(account.accountBalance)}
                    </TableCell>
                    <TableCell className="text-right">
                      {accountTransactions.length}
                    </TableCell>
                    <TableCell className="text-right">
                      <div className="flex justify-end space-x-2">
                        <Button
                          size="sm"
                          variant="ghost"
                          onClick={() => setSelectedAccount(
                            selectedAccount?.accountId === account.accountId ? null : account
                          )}
                        >
                          <TrendingUp className="w-4 h-4" />
                        </Button>
                        <Button
                          size="sm"
                          variant="ghost"
                          onClick={() => setEditingAccount(account)}
                        >
                          <Edit className="w-4 h-4" />
                        </Button>
                        <Button
                          size="sm"
                          variant="ghost"
                          onClick={() => handleDeleteAccount(account.accountId)}
                        >
                          <Trash2 className="w-4 h-4" />
                        </Button>
                      </div>
                    </TableCell>
                  </TableRow>
                );
              })}
            </TableBody>
          </Table>
          
          {accounts.length === 0 && (
            <div className="text-center py-8">
              <p className="text-muted-foreground mb-4">No accounts found</p>
              <Button onClick={() => setShowForm(true)}>
                <Plus className="w-4 h-4 mr-2" />
                Add Your First Account
              </Button>
            </div>
          )}
        </CardContent>
      </Card>

      {/* Recent Activity */}
      {selectedAccount && (
        <Card>
          <CardHeader>
            <CardTitle>{selectedAccount.accountName} - Recent Activity</CardTitle>
          </CardHeader>
          <CardContent>
            <Table>
              <TableHeader>
                <TableRow>
                  <TableHead>Date</TableHead>
                  <TableHead>Description</TableHead>
                  <TableHead>Category</TableHead>
                  <TableHead className="text-right">Amount</TableHead>
                </TableRow>
              </TableHeader>
              <TableBody>
                {getAccountTransactions(selectedAccount.accountId)
                  .sort((a, b) => new Date(b.transactionDate).getTime() - new Date(a.transactionDate).getTime())
                  .slice(0, 10)
                  .map((transaction) => (
                    <TableRow key={transaction.transactionId}>
                      <TableCell>
                        {formatDate(transaction.transactionDate)}
                      </TableCell>
                      <TableCell className="font-medium">
                        {transaction.description}
                      </TableCell>
                      <TableCell>
                        {transaction.categoryId.categoryName}
                      </TableCell>
                      <TableCell className={`text-right font-medium ${
                        transaction.type === 'income' ? 'text-green-600' : 'text-red-600'
                      }`}>
                        {transaction.type === 'income' ? '+' : '-'}{formatCurrency(transaction.amount)}
                      </TableCell>
                    </TableRow>
                  ))}
              </TableBody>
            </Table>
            
            {getAccountTransactions(selectedAccount.accountId).length === 0 && (
              <div className="text-center py-8 text-muted-foreground">
                No transactions found for this account
              </div>
            )}
          </CardContent>
        </Card>
      )}
    </div>
  );
}