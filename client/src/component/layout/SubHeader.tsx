import React, { Children } from 'react'
import Application from '../manager/Application';

interface PropsInfo {
    title : string,
    children : JSX.Element
}

function SubHeader({ title, children }: PropsInfo) {
  return (
    <div>
        <header className="bg-white shadow">
          <div className="mx-auto max-w-7xl py-6 px-4 sm:px-6 lg:px-8">
            <h1 className="text-3xl font-bold tracking-tight text-gray-900">{title}</h1>
          </div>
        </header>
        <main>
          <div className="mx-auto max-w-7xl py-6 sm:px-6 lg:px-8">
          {children}
          </div>
        </main>
        
    </div>
  )
}

export default SubHeader;