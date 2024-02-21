import { useState } from 'react'
import reactLogo from './assets/react.svg'
import List from './Components/List'
import Login from './Components/Login'
import { Detail } from './Components/Detail'


function App() {
  const [count, setCount] = useState(0)
  return <Detail />
}

export default App
