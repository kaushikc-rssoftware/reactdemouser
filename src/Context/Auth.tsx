import { ReactElement, createContext, useState } from "react";


const AuthContext = createContext({isLoggedIn:false, token:'',userName:'', login:(s:string, t:string)=>{}, logout:()=>{}});
interface LoggedUser{
  children:ReactElement
}
function AuthProvider({children}:LoggedUser) {
  const [isLoggedIn, setIsLoggedIn] = useState(false);
  const [token, setToken] = useState('');
  const[userName, setUserName]=useState('');
  const login = (jwtToken:string, username:string) => {
    setIsLoggedIn(true);
    setUserName(username);
    setToken(jwtToken);
  };

  const logout = () => {
    setIsLoggedIn(false);    
    setToken('');
  };

  const value = {
    isLoggedIn,
    token,  
    userName,  
    logout,
    login
  
  }

  return <AuthContext.Provider value={value}>{children}</AuthContext.Provider> ;
}

export { AuthContext, AuthProvider };