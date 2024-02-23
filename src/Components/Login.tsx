import { useContext, useEffect, useRef, useState } from "react";
import { AuthContext } from "../Context/Auth";
import axios from "axios";
export default function Login() {

    const userRef=useRef<HTMLInputElement|null>(null);
    const passwordRef=useRef<HTMLInputElement|null>(null);
    const errRef=useRef<HTMLParagraphElement>(null);

    const[userName,setUserName]=useState('');
    const[password,setPassword]=useState('');
    const[errMsg,setErrMsg]=useState('');
    const[success,setSuccess]=useState(false);
    const { login } = useContext(AuthContext);
    const loginRepoLink:string="https://api.escuelajs.co/api/v1/auth/login";
    useEffect(()=>{
        userRef.current?.focus();
    },[])
    useEffect(()=>{
        setErrMsg('');
    },[userName,password])

const handleSubmit=(event:any)=>{
event.preventDefault();
axios.post(loginRepoLink, {"email":userName,"password":password})
    .then(resp=> {
        if(resp.data["access_token"]!=="")
        {
            login(resp.data["access_token"],userName);
        }
        else
        {
            window.alert("use different credential")
        }
     })
    .catch(error=>{window.alert(error.message);})
    

}

    return (
        <div className="bg-gray-800 relative flex flex-col justify-center min-h-screen overflow-hidden">
            <div className="w-full p-6 m-auto bg-gray-700 text-white  rounded-md shadow-xl shadow-rose-600/40 ring ring-2 ring-purple-600 lg:max-w-xl">
                <h1 className="text-3xl font-semibold text-center text-purple-700 underline uppercase decoration-wavy">
                   Sign in
                </h1>
                <p ref={errRef} className={errMsg?"errMsg":"offscreen"} aria-live="assertive">{errMsg}</p>
                <form className="mt-6" onSubmit={handleSubmit}>
                    <div className="mb-2">
                        <label
                            htmlFor="username"
                            className="block text-sm font-semibold text-white-800"
                        >
                            Email
                        </label>
                        <input
                            id="username"
                            name="username"
                            ref={userRef}
                            type="email"
                            autoComplete="off"
                            onChange={(e)=>setUserName(e.target.value)}
                            value={userName}
                            required
                            className="block w-full px-4 py-2 mt-2 text-purple-700 bg-white border rounded-md focus:border-purple-400 focus:ring-purple-300 focus:outline-none focus:ring focus:ring-opacity-40"
                        />
                    </div>
                    <div className="mb-2">
                        <label
                            htmlFor="password"
                            className="block text-sm font-semibold text-white-800"
                        >
                            Password
                        </label>
                        <input
                            type="password"
                            id="username"
                            name="username"
                            ref={passwordRef}                            
                            onChange={(e)=>setPassword(e.target.value)}
                            value={password}
                            required
                            className="block w-full px-4 py-2 mt-2 text-purple-700 bg-white border rounded-md focus:border-purple-400 focus:ring-purple-300 focus:outline-none focus:ring focus:ring-opacity-40"
                        />
                    </div>
                    <a
                        href="#"
                        className="text-xs text-purple-600 hover:underline"
                    >
                        Forget Password?
                    </a>
                    <div className="mt-6">
                        <button type="submit" className="w-full px-4 py-2 tracking-wide text-white transition-colors duration-200 transform bg-purple-700 rounded-md hover:bg-purple-600 focus:outline-none focus:bg-purple-600">
                            Login
                        </button>
                    </div>
                </form>

                <p className="mt-8 text-xs font-light text-center text-white-700">
                    {" "}
                    Don't have an account?{" "}
                    <a
                        href="#"
                        className="font-medium text-purple-600 hover:underline"
                    >
                        Sign up
                    </a>
                </p>
            </div>
        </div>
    );
}
