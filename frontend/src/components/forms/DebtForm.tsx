import React, { useState } from 'react';
import { Button } from '@/components/ui/button';
import { Input } from '@/components/ui/input';
import { Label } from '@/components/ui/label';
import { Card, CardContent, CardHeader, CardTitle } from '@/components/ui/card';
import type { CreateDebtDTO } from '@/types/api';

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

  const [errors, setErrors] = useState<{ [key: string]: string }>({});

  const validateForm = () => {
    const newErrors: { [key: string]: string } = {};

    if (!formData.debtName.trim()) {
      newErrors.debtName = 'Debt name is required';
    } else if (formData.debtName.trim().length < 2) {
      newErrors.debtName = 'Debt name must be at least 2 characters';
    }

    if (!formData.totalOwed || formData.totalOwed <= 0) {
      newErrors.totalOwed = 'Total owed must be greater than 0';
    }

    if (formData.amountPaid && formData.amountPaid < 0) {
      newErrors.amountPaid = 'Amount paid cannot be negative';
    }

    if (formData.amountPaid && formData.amountPaid > formData.totalOwed) {
      newErrors.amountPaid = 'Amount paid cannot exceed total owed';
    }

    if (!formData.monthlyPayment || formData.monthlyPayment <= 0) {
      newErrors.monthlyPayment = 'Monthly payment must be greater than 0';
    }

    setErrors(newErrors);
    return Object.keys(newErrors).length === 0;
  };

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    
    if (validateForm()) {
      onSubmit({
        ...formData,
        debtName: formData.debtName.trim()
      });
    }
  };

  const handleInputChange = (field: keyof CreateDebtDTO, value: any) => {
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
          {initialData ? 'Edit Debt' : 'Add New Debt'}
        </CardTitle>
      </CardHeader>
      <CardContent>
        <form onSubmit={handleSubmit} className="space-y-4">
          <div>
            <Label htmlFor="debtName">Debt Name *</Label>
            <Input
              id="debtName"
              type="text"
              value={formData.debtName}
              onChange={(e) => handleInputChange('debtName', e.target.value)}
              placeholder="e.g., Credit Card, Student Loan"
              className={errors.debtName ? 'border-red-500' : ''}
              required
            />
            {errors.debtName && (
              <p className="text-sm text-red-500 mt-1">{errors.debtName}</p>
            )}
          </div>

          <div className="grid grid-cols-2 gap-4">
            <div>
              <Label htmlFor="totalOwed">Total Owed *</Label>
              <Input
                id="totalOwed"
                type="number"
                step="0.01"
                min="0.01"
                value={formData.totalOwed}
                onChange={(e) => handleInputChange('totalOwed', parseFloat(e.target.value) || 0)}
                placeholder="0.00"
                className={errors.totalOwed ? 'border-red-500' : ''}
                required
              />
              {errors.totalOwed && (
                <p className="text-sm text-red-500 mt-1">{errors.totalOwed}</p>
              )}
            </div>

            <div>
              <Label htmlFor="amountPaid">Amount Paid</Label>
              <Input
                id="amountPaid"
                type="number"
                step="0.01"
                min="0"
                value={formData.amountPaid}
                onChange={(e) => handleInputChange('amountPaid', parseFloat(e.target.value) || 0)}
                placeholder="0.00"
                className={errors.amountPaid ? 'border-red-500' : ''}
              />
              {errors.amountPaid && (
                <p className="text-sm text-red-500 mt-1">{errors.amountPaid}</p>
              )}
            </div>
          </div>

          <div>
            <Label htmlFor="monthlyPayment">Monthly Payment *</Label>
            <Input
              id="monthlyPayment"
              type="number"
              step="0.01"
              min="0.01"
              value={formData.monthlyPayment}
              onChange={(e) => handleInputChange('monthlyPayment', parseFloat(e.target.value) || 0)}
              placeholder="0.00"
              className={errors.monthlyPayment ? 'border-red-500' : ''}
              required
            />
            {errors.monthlyPayment && (
              <p className="text-sm text-red-500 mt-1">{errors.monthlyPayment}</p>
            )}
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