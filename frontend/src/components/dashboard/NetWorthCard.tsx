import React from 'react';
import { TrendingUp, TrendingDown } from 'lucide-react';
import { Card, CardContent, CardHeader, CardTitle } from '@/components/ui/card';

interface NetWorthCardProps {
  assets: number;
  debts: number;
  previousNetWorth?: number;
}

export function NetWorthCard({ assets, debts, previousNetWorth = 0 }: NetWorthCardProps) {
  const netWorth = assets - debts;
  const change = netWorth - previousNetWorth;
  const changePercent = previousNetWorth !== 0 ? (change / Math.abs(previousNetWorth)) * 100 : 0;
  const isPositive = change >= 0;

  const formatCurrency = (amount: number) => {
    return new Intl.NumberFormat('en-CA', {
      style: 'currency',
      currency: 'CAD',
    }).format(amount);
  };

  return (
    <Card>
      <CardHeader className="flex flex-row items-center justify-between space-y-0 pb-2">
        <CardTitle className="text-sm font-medium">Net Worth</CardTitle>
        {isPositive ? (
          <TrendingUp className="h-4 w-4 text-green-600" />
        ) : (
          <TrendingDown className="h-4 w-4 text-red-600" />
        )}
      </CardHeader>
      <CardContent>
        <div className="text-2xl font-bold">{formatCurrency(netWorth)}</div>
        <div className="flex items-center space-x-2 text-xs text-muted-foreground mt-1">
          <span className={isPositive ? 'text-green-600' : 'text-red-600'}>
            {isPositive ? '+' : ''}{formatCurrency(change)}
          </span>
          <span>
            ({isPositive ? '+' : ''}{changePercent.toFixed(1)}%)
          </span>
        </div>
        <div className="mt-4 grid grid-cols-2 gap-4 text-sm">
          <div>
            <p className="text-muted-foreground">Assets</p>
            <p className="font-medium text-green-600">{formatCurrency(assets)}</p>
          </div>
          <div>
            <p className="text-muted-foreground">Debts</p>
            <p className="font-medium text-red-600">{formatCurrency(debts)}</p>
          </div>
        </div>
      </CardContent>
    </Card>
  );
}