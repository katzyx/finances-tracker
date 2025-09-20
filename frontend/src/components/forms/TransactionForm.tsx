import React, { useState, useEffect } from 'react';
import { Button } from '@/components/ui/button';
import { Input } from '@/components/ui/input';
import { Label } from '@/components/ui/label';
import { Select } from '@/components/ui/select';
import { Card, CardContent, CardHeader, CardTitle } from '@/components/ui/card';
import type { CreateTransactionDTO, Account, Category, Debt } from '@/types/api';
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
    amount: initialData?.amount || undefined, // Changed from 0 to undefined
    description: initialData?.description || '',
    categoryId: initialData?.categoryId || 0,
    debtId: initialData?.debtId,
    type: initialData?.type || 'expense',
    recurrence: initialData?.recurrence,
  });

  const [errors, setErrors] = useState<{ [key: string]: string }>({});

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

  const validateForm = () => {
    const newErrors: { [key: string]: string } = {};

    if (!formData.accountId || formData.accountId === 0) {
      newErrors.accountId = 'Please select an account';
    }

    if (!formData.amount || formData.amount <= 0) {
      newErrors.amount = 'Amount must be greater than 0';
    }

    if (!formData.description.trim()) {
      newErrors.description = 'Description is required';
    } else if (formData.description.trim().length < 3) {
      newErrors.description = 'Description must be at least 3 characters';
    }

    if (!formData.categoryId || formData.categoryId === 0) {
      newErrors.categoryId = 'Please select a category';
    }

    if (!formData.type) {
      newErrors.type = 'Please select transaction type';
    }

    setErrors(newErrors);
    return Object.keys(newErrors).length === 0;
  };

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    
    if (validateForm()) {
      onSubmit({
        ...formData,
        amount: formData.amount || 0 // Ensure amount is a number
      });
    }
  };

  const handleInputChange = (field: keyof CreateTransactionDTO, value: any) => {
    setFormData(prev => ({
      ...prev,
      [field]: value
    }));

    // Clear error when user makes a change
    if (errors[field]) {
      setErrors(prev => ({ ...prev, [field]: '' }));
    }
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
              <Label htmlFor="type">Type *</Label>
              <Select
                id="type"
                value={formData.type}
                onChange={(e) => handleInputChange('type', e.target.value as 'income' | 'expense')}
                className={errors.type ? 'border-red-500' : ''}
                required
              >
                <option value="">Select Type</option>
                <option value="expense">Expense</option>
                <option value="income">Income</option>
              </Select>
              {errors.type && (
                <p className="text-sm text-red-500 mt-1">{errors.type}</p>
              )}
            </div>

            <div>
              <Label htmlFor="amount">Amount *</Label>
              <Input
                id="amount"
                type="number"
                step="0.01"
                min="0.01"
                value={formData.amount !== undefined ? formData.amount : ''} // Fixed: show empty string if undefined
                onChange={(e) => {
                  const value = e.target.value;
                  handleInputChange('amount', value === '' ? undefined : parseFloat(value));
                }}
                placeholder="0.00"
                className={errors.amount ? 'border-red-500' : ''}
                required
              />
              {errors.amount && (
                <p className="text-sm text-red-500 mt-1">{errors.amount}</p>
              )}
            </div>
          </div>

          <div>
            <Label htmlFor="description">Description *</Label>
            <Input
              id="description"
              type="text"
              value={formData.description}
              onChange={(e) => handleInputChange('description', e.target.value)}
              placeholder="Enter transaction description"
              className={errors.description ? 'border-red-500' : ''}
              required
            />
            {errors.description && (
              <p className="text-sm text-red-500 mt-1">{errors.description}</p>
            )}
          </div>

          <div className="grid grid-cols-2 gap-4">
            <div>
              <Label htmlFor="accountId">Account *</Label>
              <Select
                id="accountId"
                value={formData.accountId}
                onChange={(e) => handleInputChange('accountId', parseInt(e.target.value))}
                className={errors.accountId ? 'border-red-500' : ''}
                required
              >
                <option value={0}>Select Account</option>
                {accounts.map((account) => (
                  <option key={account.accountId} value={account.accountId}>
                    {account.accountName}
                  </option>
                ))}
              </Select>
              {errors.accountId && (
                <p className="text-sm text-red-500 mt-1">{errors.accountId}</p>
              )}
            </div>

            <div>
              <Label htmlFor="categoryId">Category *</Label>
              <Select
                id="categoryId"
                value={formData.categoryId}
                onChange={(e) => handleInputChange('categoryId', parseInt(e.target.value))}
                className={errors.categoryId ? 'border-red-500' : ''}
                required
              >
                <option value={0}>Select Category</option>
                {categories.map((category) => (
                  <option key={category.categoryId} value={category.categoryId}>
                    {category.categoryName}
                  </option>
                ))}
              </Select>
              {errors.categoryId && (
                <p className="text-sm text-red-500 mt-1">{errors.categoryId}</p>
              )}
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
              <Label htmlFor="recurrence">Recurrence (Optional)</Label>
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