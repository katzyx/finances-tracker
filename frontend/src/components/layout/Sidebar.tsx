import React from 'react';
import { Link, useLocation } from 'react-router-dom';
import { 
  Home, 
  CreditCard, 
  TrendingUp, 
  DollarSign,
  Settings,
  HelpCircle
} from 'lucide-react';
import { cn } from '../../lib/utils';

const navigation = [
  { name: 'Home', href: '/', icon: Home },
  { name: 'Monthly Overview', href: '/transactions', icon: TrendingUp },
  { name: 'Accounts', href: '/accounts', icon: CreditCard },
  { name: 'Debts & Obligations', href: '/debts', icon: DollarSign },
];

const secondaryNavigation = [
  { name: 'Settings', href: '/settings', icon: Settings },
  { name: 'Help', href: '/help', icon: HelpCircle },
];

export function Sidebar() {
  const location = useLocation();

  return (
    <div className="flex h-full w-64 flex-col bg-white border-r border-gray-200">
      <div className="flex h-16 shrink-0 items-center px-6">
        <div className="flex items-center">
          <div className="w-8 h-8 bg-green-600 rounded-lg flex items-center justify-center">
            <DollarSign className="w-5 h-5 text-white" />
          </div>
          <span className="ml-3 text-xl font-semibold text-gray-900">Finances</span>
        </div>
      </div>
      
      <nav className="flex flex-1 flex-col">
        <ul className="flex flex-1 flex-col gap-y-7 px-6">
          <li>
            <ul className="space-y-1">
              {navigation.map((item) => (
                <li key={item.name}>
                  <Link
                    to={item.href}
                    className={cn(
                      location.pathname === item.href
                        ? 'bg-green-50 text-green-700'
                        : 'text-gray-700 hover:text-green-700 hover:bg-green-50',
                      'group flex gap-x-3 rounded-md p-2 text-sm leading-6 font-semibold transition-colors'
                    )}
                  >
                    <item.icon
                      className={cn(
                        location.pathname === item.href ? 'text-green-700' : 'text-gray-400 group-hover:text-green-700',
                        'h-5 w-5 shrink-0'
                      )}
                    />
                    {item.name}
                  </Link>
                </li>
              ))}
            </ul>
          </li>
          
          <li className="mt-auto">
            <ul className="space-y-1">
              {secondaryNavigation.map((item) => (
                <li key={item.name}>
                  <Link
                    to={item.href}
                    className={cn(
                      location.pathname === item.href
                        ? 'bg-gray-50 text-gray-900'
                        : 'text-gray-700 hover:text-gray-900 hover:bg-gray-50',
                      'group flex gap-x-3 rounded-md p-2 text-sm leading-6 font-semibold'
                    )}
                  >
                    <item.icon
                      className={cn(
                        location.pathname === item.href ? 'text-gray-900' : 'text-gray-400 group-hover:text-gray-900',
                        'h-5 w-5 shrink-0'
                      )}
                    />
                    {item.name}
                  </Link>
                </li>
              ))}
            </ul>
          </li>
        </ul>
      </nav>
    </div>
  );
}