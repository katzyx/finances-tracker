import React, { useState, useEffect } from 'react';
import { Button } from '@/components/ui/button';
import { Input } from '@/components/ui/input';
import { Label } from '@/components/ui/label';
import { Select } from '@/components/ui/select';
import { Card, CardContent, CardHeader, CardTitle } from '@/components/ui/card';
import { type CreateTransactionDTO, type Account, type Category, type Debt } from '@/types/api';
import { apiService } from '@/services/api';

interface TransactionFormProps {
  onSubmit: (transaction: CreateTransactionDTO) => void;
  onCancel: () => void;
  initialData?: Partial<CreateTransactionDTO>;
}

export function TransactionForm({ onSubmit, onCancel, initialData }: TransactionFormProps) {
  const [accounts, setAccounts] = useState<Account[]>([]);
  const [categories, setCategories] = useState<Category[]>([]);
  const [debts, setDebts] = useState<Debt[]>([]);
  const [loading, setLoading] = useState(true);

  const [formData, setFormData] = useState<CreateTransactionDTO>({
    accountId: initialData?.accountId || 0,
    userId: 1, // Single user app
    amount: initialData?.amount || 0,
    description: initialData?.description || '',
    categoryId: initialData?.categoryId || 0,
    debtId: initialData?.debtId,
    type: initialData?.type || 'expense',
    recurrence: initialData?.recurrence,
  });

  useEffect(() => {
    const loadData = async () => {
      try {
        const [accountsRes, categoriesRes, debtsRes] = await Promise.all([
          apiService.getAccountsByUserId(1),
          apiService.getCategories(),
          apiService.getActiveDebtsByUserId(1),
        ]);
        setAccounts(accountsRes);
        setCategories(categoriesRes);
        setDebts(debtsRes);
      } catch (error) {
        console.error('Error loading form data:', error);
      } finally {
        setLoading(false);
      }
    };

    loadData();
  }, []);

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    onSubmit(formData);
  };

  const handleInputChange = (field: keyof CreateTransactionDTO, value: any) => {
    setFormData(prev => ({
      ...prev,
      [field]: value
    }));
  };

  if (loading) {
    return <div>Loading...</div>;
  }

  return (
    <Card>
      <CardHeader>
        <CardTitle>
          {initialData ? 'Edit Transaction' : 'Add New Transaction'}
        </CardTitle>
      </CardHeader>
      <CardContent>
        <form onSubmit={handleSubmit} className="space-y-4">
          <div className="grid grid-cols-2 gap-4">
            <div>
              <Label htmlFor="type">Type</Label>
              <Select
                id="type"
                value={formData.type}
                onChange={(e) => handleInputChange('type', e.target.value as 'income' | 'expense')}
                required
              >
                <option value="expense">Expense</option>
                <option value="income">Income</option>
              </Select>
            </div>

            <div>
              <Label htmlFor="amount">Amount</Label>
              <Input
                id="amount"
                type="number"
                step="0.01"
                min="0.01"
                value={formData.amount}
                onChange={(e) => handleInputChange('amount', parseFloat(e.target.value))}
                placeholder="0.00"
                required
              />
            </div>
          </div>

          <div>
            <Label htmlFor="description">Description</Label>
            <Input
              id="description"
              type="text"
              value={formData.description}
              onChange={(e) => handleInputChange('description', e.target.value)}
              placeholder="Enter transaction description"
              required
            />
          </div>

          <div className="grid grid-cols-2 gap-4">
            <div>
              <Label htmlFor="accountId">Account</Label>
              <Select
                id="accountId"
                value={formData.accountId}
                onChange={(e) => handleInputChange('accountId', parseInt(e.target.value))}
                required
              >
                <option value={0}>Select Account</option>
                {accounts.map((account) => (
                  <option key={account.accountId} value={account.accountId}>
                    {account.accountName}
                  </option>
                ))}
              </Select>
            </div>

            <div>
              <Label htmlFor="categoryId">Category</Label>
              <Select
                id="categoryId"
                value={formData.categoryId}
                onChange={(e) => handleInputChange('categoryId', parseInt(e.target.value))}
                required
              >
                <option value={0}>Select Category</option>
                {categories.map((category) => (
                  <option key={category.categoryId} value={category.categoryId}>
                    {category.categoryName}
                  </option>
                ))}
              </Select>
            </div>
          </div>

          <div className="grid grid-cols-2 gap-4">
            <div>
              <Label htmlFor="debtId">Debt (Optional)</Label>
              <Select
                id="debtId"
                value={formData.debtId || ''}
                onChange={(e) => handleInputChange('debtId', e.target.value ? parseInt(e.target.value) : undefined)}
              >
                <option value="">None</option>
                {debts.map((debt) => (
                  <option key={debt.debtId} value={debt.debtId}>
                    {debt.debtName}
                  </option>
                ))}
              </Select>
            </div>

            <div>
              <Label htmlFor="recurrence">Recurrence</Label>
              <Select
                id="recurrence"
                value={formData.recurrence || ''}
                onChange={(e) => handleInputChange('recurrence', e.target.value || undefined)}
              >
                <option value="">None</option>
                <option value="weekly">Weekly</option>
                <option value="monthly">Monthly</option>
                <option value="yearly">Yearly</option>
              </Select>
            </div>
          </div>

          <div className="flex justify-end space-x-2 pt-4">
            <Button type="button" variant="outline" onClick={onCancel}>
              Cancel
            </Button>
            <Button type="submit">
              {initialData ? 'Update Transaction' : 'Add Transaction'}
            </Button>
          </div>
        </form>
      </CardContent>
    </Card>
  );
}