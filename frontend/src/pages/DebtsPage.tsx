import React, { useState, useEffect } from 'react';
import { Plus, Edit, Trash2, CreditCard } from 'lucide-react';
import { PageHeader } from '@/components/layout/PageHeader';
import { Button } from '@/components/ui/button';
import { Card, CardContent, CardHeader, CardTitle } from '@/components/ui/card';
import { Table, TableBody, TableCell, TableHead, TableHeader, TableRow } from '@/components/ui/table';
import { Progress } from '@/components/ui/progress';
import { Input } from '@/components/ui/input';
import { Label } from '@/components/ui/label';
import { DebtForm } from '@/components/forms/DebtForm';
import { type Debt, type CreateDebtDTO } from '@/types/api';
import { apiService } from '@/services/api';
import { formatCurrency } from '@/lib/utils';

export function DebtsPage() {
  const [debts, setDebts] = useState<Debt[]>([]);
  const [loading, setLoading] = useState(true);
  const [showForm, setShowForm] = useState(false);
  const [editingDebt, setEditingDebt] = useState<Debt | null>(null);
  const [paymentAmount, setPaymentAmount] = useState<Record<number, string>>({});

  useEffect(() => {
    loadDebts();
  }, []);

  const loadDebts = async () => {
    try {
      const debtsRes = await apiService.getDebtsByUserId(1);
      setDebts(debtsRes);
    } catch (error) {
      console.error('Error loading debts:', error);
    } finally {
      setLoading(false);
    }
  };

  const handleCreateDebt = async (debtData: CreateDebtDTO) => {
    try {
      await apiService.createDebt(debtData);
      await loadDebts();
      setShowForm(false);
    } catch (error) {
      console.error('Error creating debt:', error);
    }
  };

  const handleUpdateDebt = async (debtData: CreateDebtDTO) => {
    if (!editingDebt) return;
    
    try {
      const updatedDebt: Debt = {
        ...editingDebt,
        debtName: debtData.debtName,
        totalOwed: debtData.totalOwed,
        amountPaid: debtData.amountPaid || 0,
        monthlyPayment: debtData.monthlyPayment,
        remainingBalance: debtData.totalOwed - (debtData.amountPaid || 0),
        paymentProgress: ((debtData.amountPaid || 0) / debtData.totalOwed) * 100,
      };
      
      await apiService.updateDebt(editingDebt.debtId, updatedDebt);
      await loadDebts();
      setEditingDebt(null);
    } catch (error) {
      console.error('Error updating debt:', error);
    }
  };

  const handleDeleteDebt = async (id: number) => {
    if (window.confirm('Are you sure you want to delete this debt?')) {
      try {
        await apiService.deleteDebt(id);
        await loadDebts();
      } catch (error) {
        console.error('Error deleting debt:', error);
      }
    }
  };

  const handleMakePayment = async (debtId: number) => {
    const amount = parseFloat(paymentAmount[debtId] || '0');
    if (amount <= 0) return;

    try {
      await apiService.makeDebtPayment(debtId, { paymentAmount: amount });
      await loadDebts();
      setPaymentAmount(prev => ({ ...prev, [debtId]: '' }));
    } catch (error) {
      console.error('Error making payment:', error);
      alert('Error making payment. Please check the amount and try again.');
    }
  };

  const totalDebt = debts.reduce((sum, debt) => sum + debt.totalOwed, 0);
  const totalPaid = debts.reduce((sum, debt) => sum + debt.amountPaid, 0);
  const totalRemaining = debts.reduce((sum, debt) => sum + debt.remainingBalance, 0);
  const overallProgress = totalDebt > 0 ? (totalPaid / totalDebt) * 100 : 0;
  const monthlyPayments = debts.reduce((sum, debt) => sum + debt.monthlyPayment, 0);

  const activeDebts = debts.filter(debt => debt.remainingBalance > 0);
  const paidOffDebts = debts.filter(debt => debt.remainingBalance <= 0);

  if (loading) {
    return <div className="flex items-center justify-center h-64">Loading...</div>;
  }

  if (showForm || editingDebt) {
    return (
      <div>
        <PageHeader title="Debts & Obligations" />
        <DebtForm
          onSubmit={editingDebt ? handleUpdateDebt : handleCreateDebt}
          onCancel={() => {
            setShowForm(false);
            setEditingDebt(null);
          }}
          initialData={editingDebt ? {
            debtName: editingDebt.debtName,
            totalOwed: editingDebt.totalOwed,
            amountPaid: editingDebt.amountPaid,
            monthlyPayment: editingDebt.monthlyPayment,
          } : undefined}
        />
      </div>
    );
  }

  return (
    <div>
      <PageHeader
        title="Debts & Obligations"
        subtitle="Track and manage your debts and loan payments"
      >
        <Button onClick={() => setShowForm(true)}>
          <Plus className="w-4 h-4 mr-2" />
          Add Debt
        </Button>
      </PageHeader>

      {/* Summary Cards */}
      <div className="grid grid-cols-1 lg:grid-cols-4 gap-6 mb-8">
        <Card>
          <CardContent className="p-6">
            <div className="flex items-center justify-between">
              <div>
                <p className="text-sm text-muted-foreground">Total Remaining</p>
                <p className="text-2xl font-bold text-red-600">{formatCurrency(totalRemaining)}</p>
              </div>
              <CreditCard className="w-8 h-8 text-red-600" />
            </div>
          </CardContent>
        </Card>

        <Card>
          <CardContent className="p-6">
            <div className="flex items-center justify-between">
              <div>
                <p className="text-sm text-muted-foreground">Total Paid</p>
                <p className="text-2xl font-bold text-green-600">{formatCurrency(totalPaid)}</p>
              </div>
            </div>
          </CardContent>
        </Card>

        <Card>
          <CardContent className="p-6">
            <div className="flex items-center justify-between">
              <div>
                <p className="text-sm text-muted-foreground">Monthly Payments</p>
                <p className="text-2xl font-bold">{formatCurrency(monthlyPayments)}</p>
              </div>
            </div>
          </CardContent>
        </Card>

        <Card>
          <CardContent className="p-6">
            <div className="flex items-center justify-between">
              <div>
                <p className="text-sm text-muted-foreground">Overall Progress</p>
                <p className="text-2xl font-bold">{overallProgress.toFixed(1)}%</p>
              </div>
            </div>
          </CardContent>
        </Card>
      </div>

      {/* Overall Progress */}
      {totalDebt > 0 && (
        <Card className="mb-8">
          <CardHeader>
            <CardTitle>Overall Debt Progress</CardTitle>
          </CardHeader>
          <CardContent>
            <Progress value={overallProgress} className="h-4 mb-4" />
            <div className="flex justify-between text-sm text-muted-foreground">
              <span>{formatCurrency(totalPaid)} paid</span>
              <span>{formatCurrency(totalRemaining)} remaining</span>
            </div>
          </CardContent>
        </Card>
      )}

      {/* Active Debts */}
      {activeDebts.length > 0 && (
        <Card className="mb-8">
          <CardHeader>
            <CardTitle>Active Debts ({activeDebts.length})</CardTitle>
          </CardHeader>
          <CardContent>
            <Table>
              <TableHeader>
                <TableRow>
                  <TableHead>Debt Name</TableHead>
                  <TableHead className="text-right">Total Owed</TableHead>
                  <TableHead className="text-right">Paid</TableHead>
                  <TableHead className="text-right">Remaining</TableHead>
                  <TableHead>Progress</TableHead>
                  <TableHead className="text-right">Monthly Payment</TableHead>
                  <TableHead className="text-right">Make Payment</TableHead>
                  <TableHead className="text-right">Actions</TableHead>
                </TableRow>
              </TableHeader>
              <TableBody>
                {activeDebts.map((debt) => (
                  <TableRow key={debt.debtId}>
                    <TableCell className="font-medium">
                      {debt.debtName}
                    </TableCell>
                    <TableCell className="text-right">
                      {formatCurrency(debt.totalOwed)}
                    </TableCell>
                    <TableCell className="text-right text-green-600">
                      {formatCurrency(debt.amountPaid)}
                    </TableCell>
                    <TableCell className="text-right text-red-600 font-medium">
                      {formatCurrency(debt.remainingBalance)}
                    </TableCell>
                    <TableCell className="min-w-32">
                      <div className="space-y-2">
                        <Progress value={debt.paymentProgress} className="h-2" />
                        <div className="text-xs text-muted-foreground text-center">
                          {debt.paymentProgress.toFixed(1)}%
                        </div>
                      </div>
                    </TableCell>
                    <TableCell className="text-right">
                      {formatCurrency(debt.monthlyPayment)}
                    </TableCell>
                    <TableCell className="text-right min-w-48">
                      <div className="flex space-x-2">
                        <Input
                          type="number"
                          step="0.01"
                          min="0.01"
                          max={debt.remainingBalance}
                          placeholder="Amount"
                          value={paymentAmount[debt.debtId] || ''}
                          onChange={(e) => setPaymentAmount(prev => ({
                            ...prev,
                            [debt.debtId]: e.target.value
                          }))}
                          className="w-24"
                        />
                        <Button
                          size="sm"
                          onClick={() => handleMakePayment(debt.debtId)}
                          disabled={!paymentAmount[debt.debtId] || parseFloat(paymentAmount[debt.debtId]) <= 0}
                        >
                          Pay
                        </Button>
                      </div>
                    </TableCell>
                    <TableCell className="text-right">
                      <div className="flex justify-end space-x-2">
                        <Button
                          size="sm"
                          variant="ghost"
                          onClick={() => setEditingDebt(debt)}
                        >
                          <Edit className="w-4 h-4" />
                        </Button>
                        <Button
                          size="sm"
                          variant="ghost"
                          onClick={() => handleDeleteDebt(debt.debtId)}
                        >
                          <Trash2 className="w-4 h-4" />
                        </Button>
                      </div>
                    </TableCell>
                  </TableRow>
                ))}
              </TableBody>
            </Table>
          </CardContent>
        </Card>
      )}

      {/* Paid Off Debts */}
      {paidOffDebts.length > 0 && (
        <Card>
          <CardHeader>
            <CardTitle>Paid Off Debts ({paidOffDebts.length})</CardTitle>
          </CardHeader>
          <CardContent>
            <Table>
              <TableHeader>
                <TableRow>
                  <TableHead>Debt Name</TableHead>
                  <TableHead className="text-right">Total Paid</TableHead>
                  <TableHead className="text-right">Actions</TableHead>
                </TableRow>
              </TableHeader>
              <TableBody>
                {paidOffDebts.map((debt) => (
                  <TableRow key={debt.debtId}>
                    <TableCell className="font-medium">
                      <div className="flex items-center">
                        {debt.debtName}
                        <span className="ml-2 inline-flex items-center px-2.5 py-0.5 rounded-full text-xs font-medium bg-green-100 text-green-800">
                          Paid Off
                        </span>
                      </div>
                    </TableCell>
                    <TableCell className="text-right text-green-600 font-medium">
                      {formatCurrency(debt.totalOwed)}
                    </TableCell>
                    <TableCell className="text-right">
                      <Button
                        size="sm"
                        variant="ghost"
                        onClick={() => handleDeleteDebt(debt.debtId)}
                      >
                        <Trash2 className="w-4 h-4" />
                      </Button>
                    </TableCell>
                  </TableRow>
                ))}
              </TableBody>
            </Table>
          </CardContent>
        </Card>
      )}

      {debts.length === 0 && (
        <Card>
          <CardContent className="text-center py-12">
            <CreditCard className="w-12 h-12 text-muted-foreground mx-auto mb-4" />
            <h3 className="text-lg font-medium text-muted-foreground mb-2">No debts found</h3>
            <p className="text-sm text-muted-foreground mb-4">
              Start tracking your debts and loans to get a better overview of your financial obligations.
            </p>
            <Button onClick={() => setShowForm(true)}>
              <Plus className="w-4 h-4 mr-2" />
              Add Your First Debt
            </Button>
          </CardContent>
        </Card>
      )}
    </div>
  );
}