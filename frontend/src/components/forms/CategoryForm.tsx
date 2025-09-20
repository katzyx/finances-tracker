import React, { useState } from 'react';
import { Button } from '@/components/ui/button';
import { Input } from '@/components/ui/input';
import { Label } from '@/components/ui/label';
import { Card, CardContent, CardHeader, CardTitle } from '@/components/ui/card';
import type { Category } from '@/types/api';

interface CategoryFormProps {
  onSubmit: (category: { categoryName: string }) => void;
  onCancel: () => void;
  initialData?: Partial<Category>;
}

export function CategoryForm({ onSubmit, onCancel, initialData }: CategoryFormProps) {
  const [formData, setFormData] = useState({
    categoryName: initialData?.categoryName || '',
  });

  const [errors, setErrors] = useState<{ [key: string]: string }>({});

  const validateForm = () => {
    const newErrors: { [key: string]: string } = {};

    if (!formData.categoryName.trim()) {
      newErrors.categoryName = 'Category name is required';
    } else if (formData.categoryName.trim().length < 2) {
      newErrors.categoryName = 'Category name must be at least 2 characters';
    }

    setErrors(newErrors);
    return Object.keys(newErrors).length === 0;
  };

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    
    if (validateForm()) {
      onSubmit({
        categoryName: formData.categoryName.trim()
      });
    }
  };

  const handleInputChange = (value: string) => {
    setFormData({ categoryName: value });
    
    // Clear error when user starts typing
    if (errors.categoryName && value.trim()) {
      setErrors(prev => ({ ...prev, categoryName: '' }));
    }
  };

  return (
    <Card>
      <CardHeader>
        <CardTitle>
          {initialData ? 'Edit Category' : 'Add New Category'}
        </CardTitle>
      </CardHeader>
      <CardContent>
        <form onSubmit={handleSubmit} className="space-y-4">
          <div>
            <Label htmlFor="categoryName">Category Name</Label>
            <Input
              id="categoryName"
              type="text"
              value={formData.categoryName}
              onChange={(e) => handleInputChange(e.target.value)}
              placeholder="e.g., Groceries, Entertainment, Utilities"
              className={errors.categoryName ? 'border-red-500' : ''}
            />
            {errors.categoryName && (
              <p className="text-sm text-red-500 mt-1">{errors.categoryName}</p>
            )}
          </div>

          <div className="flex justify-end space-x-2 pt-4">
            <Button type="button" variant="outline" onClick={onCancel}>
              Cancel
            </Button>
            <Button type="submit">
              {initialData ? 'Update Category' : 'Add Category'}
            </Button>
          </div>
        </form>
      </CardContent>
    </Card>
  );
}