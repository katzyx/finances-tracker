import React, { useState } from 'react';
import { Button } from '@/components/ui/button';
import { Input } from '@/components/ui/input';
import { Label } from '@/components/ui/label';
import { Card, CardContent, CardHeader, CardTitle } from '@/components/ui/card';
import { CreateDebtDTO } from '@/types/api';

interface DebtFormProps {
  onSubmit: (debt: CreateDebtDTO) => void;
  onCancel: () => void;
  initialData?: Partial<CreateDebtDTO>;
}

export function DebtForm({ onSubmit, onCancel, initialData }: DebtFormProps) {
  const [formData, setFormData] = useState<CreateDebtDTO>({
    userId: 1, // Single user app
    debtName: initialData?.debtName || '',
    totalOwed: initialData?.totalOwed || 0,
    amountPaid: initialData?.amountPaid || 0,
    monthlyPayment: initialData?.monthlyPayment || 0,
  });

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    onSubmit(formData);
  };

  const handleInputChange = (field: keyof CreateDebtDTO, value: any) => {
    setFormData(prev => ({
      ...prev,
      [field]: value
    }));
  };

  return (
    <Card>
      <CardHeader>
        <CardTitle>
          {initialData ? 'Edit Debt' : 'Add New Debt'}
        </CardTitle>
      </CardHeader>
      <CardContent>
        <form onSubmit={handleSubmit} className="space-y-4">
          <div>
            <Label htmlFor="debtName">Debt Name</Label>
            <Input
              id="debtName"
              type="text"
              value={formData.debtName}
              onChange={(e) => handleInputChange('debtName', e.target.value)}
              placeholder="e.g., Credit Card, Student Loan"
              required
            />
          </div>

          <div className="grid grid-cols-2 gap-4">
            <div>
              <Label htmlFor="totalOwed">Total Owed</Label>
              <Input
                id="totalOwed"
                type="number"
                step="0.01"
                min="0.01"
                value={formData.totalOwed}
                onChange={(e) => handleInputChange('totalOwed', parseFloat(e.target.value))}
                placeholder="0.00"
                required
              />
            </div>

            <div>
              <Label htmlFor="amountPaid">Amount Paid</Label>
              <Input
                id="amountPaid"
                type="number"
                step="0.01"
                min="0"
                value={formData.amountPaid}
                onChange={(e) => handleInputChange('amountPaid', parseFloat(e.target.value))}
                placeholder="0.00"
              />
            </div>
          </div>

          <div>
            <Label htmlFor="monthlyPayment">Monthly Payment</Label>
            <Input
              id="monthlyPayment"
              type="number"
              step="0.01"
              min="0.01"
              value={formData.monthlyPayment}
              onChange={(e) => handleInputChange('monthlyPayment', parseFloat(e.target.value))}
              placeholder="0.00"
              required
            />
          </div>

          <div className="flex justify-end space-x-2 pt-4">
            <Button type="button" variant="outline" onClick={onCancel}>
              Cancel
            </Button>
            <Button type="submit">
              {initialData ? 'Update Debt' : 'Add Debt'}
            </Button>
          </div>
        </form>
      </CardContent>
    </Card>
  );
}
