import React, { useState } from 'react';
import { Button } from '@/components/ui/button';
import { Input } from '@/components/ui/input';
import { Label } from '@/components/ui/label';
import { Card, CardContent, CardHeader, CardTitle } from '@/components/ui/card';
import { type CreateAccountDTO } from '@/types/api';

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

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    onSubmit(formData);
  };

  const handleInputChange = (field: keyof CreateAccountDTO, value: any) => {
    setFormData(prev => ({
      ...prev,
      [field]: value
    }));
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
            <Label htmlFor="accountName">Account Name</Label>
            <Input
              id="accountName"
              type="text"
              value={formData.accountName}
              onChange={(e) => handleInputChange('accountName', e.target.value)}
              placeholder="e.g., Chequing, Savings, TFSA"
              required
            />
          </div>

          <div>
            <Label htmlFor="accountBalance">Initial Balance</Label>
            <Input
              id="accountBalance"
              type="number"
              step="0.01"
              min="0"
              value={formData.accountBalance}
              onChange={(e) => handleInputChange('accountBalance', parseFloat(e.target.value))}
              placeholder="0.00"
              required
            />
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

