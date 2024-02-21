import { useState } from 'react'
import reactLogo from './assets/react.svg'
import './App.css'

function App() {
  const [count, setCount] = useState(0)

  return (
    <>
    <img src={reactLogo} className="logo react" alt="React logo" />
    <h1 className='text-x1 font-bold text-red-500'>Hello world from tailwind</h1>
    </>
  )
}

export default App
