import React from 'react'

function Footer() {
  return (
    <footer className='ml-5 absolute bottom-0 border-t w-screen pt-2'>   
        <div>
          <nav>
            <a href='https://velog.io/@nimoh' target='_blank' className='no-underline font-black'>Blog</a>
            |
            <a href='https://github.com/nimohLee' target='_blank' className='no-underline font-black'>Github</a>
          </nav>
          <p>
            <span>저자 : nimoh</span><br/>
            <span>이메일 : spakers38@gmail.com</span><br/>
            <span>Copyright 2022. nimoh. All Rights Reserved.</span>
          </p>
        </div>
    </footer>
  )
}

export default Footer