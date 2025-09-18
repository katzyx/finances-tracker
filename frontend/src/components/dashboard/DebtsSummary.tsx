import React from 'react';
import { Card, CardContent, CardHeader, CardTitle } from '../../components/ui/card';
import { Progress } from '../../components/ui/progress';
import { type Debt } from '../../types/api';
import { formatCurrency } from '../../lib/utils';

interface DebtsSummaryProps {
  debts: Debt[];
}

export function DebtsSummary({ debts }: DebtsSummaryProps) {
  const totalOwed = debts.reduce((sum, debt) => sum + debt.totalOwed, 0);
  const totalPaid = debts.reduce((sum, debt) => sum + debt.amountPaid, 0);
  const totalRemaining = totalOwed - totalPaid;
  const overallProgress = totalOwed > 0 ? (totalPaid / totalOwed) * 100 : 0;

  return (
    <Card>
      <CardHeader>
        <CardTitle>Debt Summary</CardTitle>
      </CardHeader>
      <CardContent>
        <div className="space-y-4">
          <div className="flex items-center justify-between">
            <span className="text-sm text-muted-foreground">Total Remaining</span>
            <span className="font-semibold text-red-600">{formatCurrency(totalRemaining)}</span>
          </div>
          
          <Progress value={overallProgress} className="h-2" />
          
          <div className="flex justify-between text-xs text-muted-foreground">
            <span>{overallProgress.toFixed(1)}% paid off</span>
            <span>{formatCurrency(totalPaid)} of {formatCurrency(totalOwed)}</span>
          </div>
          
          <div className="mt-4 space-y-2">
            {debts.slice(0, 3).map((debt) => (
              <div key={debt.debtId} className="flex items-center justify-between text-sm">
                <span>{debt.debtName}</span>
                <span className="text-red-600">{formatCurrency(debt.remainingBalance)}</span>
              </div>
            ))}
            {debts.length > 3 && (
              <p className="text-xs text-muted-foreground mt-2">
                +{debts.length - 3} more debts
              </p>
            )}
          </div>
        </div>
      </CardContent>
    </Card>
  );
}