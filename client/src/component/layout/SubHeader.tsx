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
            {
              title==='지원 내역'
              ?
              <div>
                <h1 className="text-3xl font-bold tracking-tight text-gray-900">{title}</h1>
                  <div>
                    <input type="text" name="" id="" placeholder='검색어를 입력하세요'/>
                  </div>
              </div>
              :<h1 className="text-3xl font-bold tracking-tight text-gray-900">{title}</h1>
            }
          </div>
        </header>
        <main>
          <div className="mx-auto max-w-7xl py-6 sm:px-6 lg:px-8 h-auto min-h-full pb-10">
          {children}
          </div>
        </main>
        
    </div>
  )
}

export default SubHeader;