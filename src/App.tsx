import { useState } from 'react'
import reactLogo from './assets/react.svg'
import MyList from './Components/ListNew'
import Login from './Components/Login'
import { Detail } from './Components/Detail'


function App() {
  const [count, setCount] = useState(0)
  const [category, setCategory] = useState('')
  return (<div>
    <select name="category" id="category" onChange={(event)=>setCategory(event.target.value)} className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500">
      <option value="Housing">Housing</option>
      <option value="Clothing">Clothing</option>
    </select>
    <MyList category={category} />
  </div>)
}

export default App
