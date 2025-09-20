import React, { useState, useEffect } from 'react';
import { Plus, Edit, Trash2 } from 'lucide-react';
import { PageHeader } from '@/components/layout/PageHeader';
import { Button } from '@/components/ui/button';
import { Card, CardContent, CardHeader, CardTitle } from '@/components/ui/card';
import { Table, TableBody, TableCell, TableHead, TableHeader, TableRow } from '@/components/ui/table';
import { CategoryForm } from '@/components/forms/CategoryForm';
import type { Category } from '@/types/api';
import { apiService } from '@/services/api';

export function CategoriesPage() {
  const [categories, setCategories] = useState<Category[]>([]);
  const [loading, setLoading] = useState(true);
  const [showForm, setShowForm] = useState(false);
  const [editingCategory, setEditingCategory] = useState<Category | null>(null);

  useEffect(() => {
    loadCategories();
  }, []);

  const loadCategories = async () => {
    try {
      const categoriesRes = await apiService.getCategories();
      setCategories(categoriesRes);
    } catch (error) {
      console.error('Error loading categories:', error);
    } finally {
      setLoading(false);
    }
  };

  const handleCreateCategory = async (categoryData: { categoryName: string }) => {
    try {
      await apiService.createCategory(categoryData);
      await loadCategories();
      setShowForm(false);
    } catch (error) {
      console.error('Error creating category:', error);
      alert('Error creating category. Please try again.');
    }
  };

  const handleUpdateCategory = async (categoryData: { categoryName: string }) => {
    if (!editingCategory) return;
    
    try {
      const updatedCategory: Category = {
        ...editingCategory,
        categoryName: categoryData.categoryName,
      };
      
      await apiService.updateCategory(editingCategory.categoryId, updatedCategory);
      await loadCategories();
      setEditingCategory(null);
    } catch (error) {
      console.error('Error updating category:', error);
      alert('Error updating category. Please try again.');
    }
  };

  const handleDeleteCategory = async (id: number) => {
    if (window.confirm('Are you sure you want to delete this category? This may affect existing transactions.')) {
      try {
        await apiService.deleteCategory(id);
        await loadCategories();
      } catch (error) {
        console.error('Error deleting category:', error);
        alert('Error deleting category. It may be in use by existing transactions.');
      }
    }
  };

  if (loading) {
    return <div className="flex items-center justify-center h-64">Loading...</div>;
  }

  if (showForm || editingCategory) {
    return (
      <div>
        <PageHeader title="Categories" />
        <CategoryForm
          onSubmit={editingCategory ? handleUpdateCategory : handleCreateCategory}
          onCancel={() => {
            setShowForm(false);
            setEditingCategory(null);
          }}
          initialData={editingCategory || undefined}
        />
      </div>
    );
  }

  return (
    <div>
      <PageHeader
        title="Category Management"
        subtitle="Organize your transactions with custom categories"
      >
        <Button onClick={() => setShowForm(true)}>
          <Plus className="w-4 h-4 mr-2" />
          Add Category
        </Button>
      </PageHeader>

      <Card>
        <CardHeader>
          <CardTitle>Categories ({categories.length})</CardTitle>
        </CardHeader>
        <CardContent>
          <Table>
            <TableHeader>
              <TableRow>
                <TableHead>Category Name</TableHead>
                <TableHead className="text-right">Actions</TableHead>
              </TableRow>
            </TableHeader>
            <TableBody>
              {categories.map((category) => (
                <TableRow key={category.categoryId}>
                  <TableCell className="font-medium">
                    {category.categoryName}
                  </TableCell>
                  <TableCell className="text-right">
                    <div className="flex justify-end space-x-2">
                      <Button
                        size="sm"
                        variant="ghost"
                        onClick={() => setEditingCategory(category)}
                        title="Edit category"
                      >
                        <Edit className="w-4 h-4" />
                      </Button>
                      <Button
                        size="sm"
                        variant="ghost"
                        onClick={() => handleDeleteCategory(category.categoryId)}
                        title="Delete category"
                      >
                        <Trash2 className="w-4 h-4" />
                      </Button>
                    </div>
                  </TableCell>
                </TableRow>
              ))}
            </TableBody>
          </Table>
          
          {categories.length === 0 && (
            <div className="text-center py-8">
              <p className="text-muted-foreground mb-4">No categories found</p>
              <Button onClick={() => setShowForm(true)}>
                <Plus className="w-4 h-4 mr-2" />
                Add Your First Category
              </Button>
            </div>
          )}
        </CardContent>
      </Card>
    </div>
  );
}