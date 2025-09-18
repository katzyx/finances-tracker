import React from 'react';
import { Card, CardContent, CardHeader, CardTitle } from '@/components/ui/card';
import { type Account } from '@/types/api';

interface AccountsListProps {
  accounts: Account[];
  onAccountClick?: (account: Account) => void;
}

export function AccountsList({ accounts, onAccountClick }: AccountsListProps) {
  const formatCurrency = (amount: number) => {
    return new Intl.NumberFormat('en-CA', {
      style: 'currency',
      currency: 'CAD',
    }).format(amount);
  };

  return (
    <Card>
      <CardHeader>
        <CardTitle>Accounts</CardTitle>
      </CardHeader>
      <CardContent className="p-0">
        <div className="divide-y">
          {accounts.map((account) => (
            <div
              key={account.accountId}
              className={`flex items-center justify-between p-4 ${
                onAccountClick ? 'cursor-pointer hover:bg-gray-50' : ''
              }`}
              onClick={() => onAccountClick?.(account)}
            >
              <div>
                <p className="font-medium">{account.accountName}</p>
              </div>
              <div className="text-right">
                <p className="font-semibold">{formatCurrency(account.accountBalance)}</p>
              </div>
            </div>
          ))}
          {accounts.length === 0 && (
            <div className="text-center py-8 text-muted-foreground">
              No accounts found
            </div>
          )}
        </div>
      </CardContent>
    </Card>
  );
}