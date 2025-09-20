import React, { useState } from 'react';
import { Button } from '@/components/ui/button';
import { Input } from '@/components/ui/input';
import { Label } from '@/components/ui/label';
import { Card, CardContent, CardHeader, CardTitle } from '@/components/ui/card';
import type { CreateAccountDTO } from '@/types/api';

interface AccountFormProps {
  onSubmit: (account: CreateAccountDTO) => void;
  onCancel: () => void;
  initialData?: Partial<CreateAccountDTO>;
}

export function AccountForm({ onSubmit, onCancel, initialData }: AccountFormProps) {
  const [formData, setFormData] = useState<CreateAccountDTO>({
    userId: 1, // Single user app
    accountName: initialData?.accountName || '',
    accountBalance: initialData?.accountBalance || 0,
  });

  const [errors, setErrors] = useState<{ [key: string]: string }>({});

  const validateForm = () => {
    const newErrors: { [key: string]: string } = {};

    if (!formData.accountName.trim()) {
      newErrors.accountName = 'Account name is required';
    } else if (formData.accountName.trim().length < 2) {
      newErrors.accountName = 'Account name must be at least 2 characters';
    }

    if (formData.accountBalance < 0) {
      newErrors.accountBalance = 'Account balance cannot be negative';
    }

    setErrors(newErrors);
    return Object.keys(newErrors).length === 0;
  };

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    
    if (validateForm()) {
      onSubmit({
        ...formData,
        accountName: formData.accountName.trim()
      });
    }
  };

  const handleInputChange = (field: keyof CreateAccountDTO, value: any) => {
    setFormData(prev => ({
      ...prev,
      [field]: value
    }));

    // Clear error when user starts typing
    if (errors[field]) {
      setErrors(prev => ({ ...prev, [field]: '' }));
    }
  };

  return (
    <Card>
      <CardHeader>
        <CardTitle>
          {initialData ? 'Edit Account' : 'Add New Account'}
        </CardTitle>
      </CardHeader>
      <CardContent>
        <form onSubmit={handleSubmit} className="space-y-4">
          <div>
            <Label htmlFor="accountName">Account Name *</Label>
            <Input
              id="accountName"
              type="text"
              value={formData.accountName}
              onChange={(e) => handleInputChange('accountName', e.target.value)}
              placeholder="e.g., Chequing, Savings, TFSA"
              className={errors.accountName ? 'border-red-500' : ''}
              required
            />
            {errors.accountName && (
              <p className="text-sm text-red-500 mt-1">{errors.accountName}</p>
            )}
          </div>

          <div>
            <Label htmlFor="accountBalance">Initial Balance</Label>
            <Input
              id="accountBalance"
              type="number"
              step="0.01"
              min="0"
              value={formData.accountBalance}
              onChange={(e) => handleInputChange('accountBalance', parseFloat(e.target.value) || 0)}
              placeholder="0.00"
              className={errors.accountBalance ? 'border-red-500' : ''}
            />
            {errors.accountBalance && (
              <p className="text-sm text-red-500 mt-1">{errors.accountBalance}</p>
            )}
          </div>

          <div className="flex justify-end space-x-2 pt-4">
            <Button type="button" variant="outline" onClick={onCancel}>
              Cancel
            </Button>
            <Button type="submit">
              {initialData ? 'Update Account' : 'Add Account'}
            </Button>
          </div>
        </form>
      </CardContent>
    </Card>
  );
}