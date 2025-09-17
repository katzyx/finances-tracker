import React from 'react';

interface PageHeaderProps {
  title: string;
  subtitle?: string;
  children?: React.ReactNode;
}

export function PageHeader({ title, subtitle, children }: PageHeaderProps) {
  return (
    <div className="mb-8 sm:flex sm:items-center sm:justify-between">
      <div>
        <h1 className="text-3xl font-bold leading-tight tracking-tight text-gray-900">
          {title}
        </h1>
        {subtitle && (
          <p className="mt-2 text-sm text-gray-700">{subtitle}</p>
        )}
      </div>
      {children && (
        <div className="mt-4 sm:ml-16 sm:mt-0 sm:flex-none">
          {children}
        </div>
      )}
    </div>
  );
}