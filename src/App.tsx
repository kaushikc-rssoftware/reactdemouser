import { useContext, useState } from 'react'
import reactLogo from './assets/react.svg'
import MyList from './Components/ListNew'
import Login from './Components/Login'
import { Detail } from './Components/Detail'
import { Link, Outlet, Route, Routes } from 'react-router-dom'
import Nav from './Components/Nav'
import { AuthContext } from './Context/Auth'


function App() {
  const [count, setCount] = useState(0)
  const [category, setCategory] = useState('');
  const { isLoggedIn, logout } = useContext(AuthContext);
  return (   
    <>
    {
      isLoggedIn  && <Nav />
      
    }
    {
      !isLoggedIn  && <Login />
    }
   <div>
    
    
    <Routes>
        <Route index path="/reactdemouser/" element={<MyList />} />
        <Route  path="/reactdemouser/detail/:id" element={<Detail />} />
        <Route path="/login" element={<Login />} />
        
      </Routes>
    <Outlet></Outlet>
  </div></>)
}

export default App
