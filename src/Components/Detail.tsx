import axios, { CanceledError } from "axios";
import { FormEvent, useContext, useEffect, useRef, useState } from "react";
import { useParams } from "react-router-dom";
import {User} from "../Model/User";
import { FieldValues, useForm } from "react-hook-form";
import { Navigate, useNavigate } from "react-router-dom";
import { AuthContext } from "../Context/Auth";
  export function Detail() {
 const {id}= useParams();
 const [isNewForm, setIsNewForm]=useState(false);
 const { isLoggedIn, token, userName } = useContext(AuthContext);
 const [originalUser,setOriginalUser]=useState<User>();
 const {register, handleSubmit, formState, setValue,reset }=useForm();
 console.log(formState.errors)
 let src:string="https://img.freepik.com/free-vector/mysterious-gangster-character-illustration_23-"+id+".jpg";
 const[isLoading, setLoading]=useState(false);
 const [user, setUser]=useState<User>();
 const repoLink="https://jsonplaceholder.typicode.com/users";
 const controller = new AbortController();
 const[error, setError]=useState('');
 const navigate = useNavigate();
const setFormValue=()=>{
setValue('name', user?.name);
setValue('email', user?.email);
setValue('phone', user?.phone);
setValue('website', user?.website);
}
 

useEffect(()=>{

    if(!isNewForm)
    {
        setLoading(true);
        axios.get<User>(repoLink+"/"+id,{signal:controller.signal, 
            headers: { 'Authorization': + token }
            })
        .then(response=>{
            //console.log(response.data);
            setUser(response.data);
            setOriginalUser(response.data);
        
        })
        .catch(err=>{console.log(err); 
            if(err instanceof CanceledError) return;
            setError(err.message)
        }).finally(()=>{setLoading(false);});
        
    //return ()=>controller.abort();
    } 
    
},[]);
useEffect(()=>{
    if(id=="new")
    {
        setIsNewForm(true);
        setOriginalUser({name:'',email:'', phone:'', website:''})
        setUser({...originalUser});
    }
})
const goList=()=>{
navigate("/reactdemouser/");
}
const onSubmit=(data:FieldValues)=>{
   if(!isNewForm)
   { 
    axios.patch(repoLink+"/"+id, user,{headers: { 'Authorization': + token }})
    .then(resp=> {window.alert("User is updated."); navigate("/reactdemouser/",{replace:true});})
    .catch(error=>{window.alert(error.message); setUser({...originalUser});})
    /*console.log(user);*/
   }
   else
   {
    setOriginalUser({name:'',email:'', phone:'', website:''})
    axios.post(repoLink, user,{headers: { 'Authorization': + token }})
    .then(resp=> {window.alert("User is saved."); navigate("/reactdemouser/",{replace:true});})
    .catch(error=>{window.alert(error.message);setUser({...originalUser});})
   }

}
    return ( 
        <form onSubmit={handleSubmit(onSubmit)}>
        <div className="bg-gray-800 relative flex flex-col justify-center min-h-screen overflow-hidden">
    <center><h1>id== {id}</h1>
    <div className="max-w-sm bg-white border border-gray-200 rounded-lg shadow dark:bg-gray-800 dark:border-gray-700">
    
    <a href="#">
        {!isNewForm && <img className="rounded-t-lg" src={src} alt="" />}
    </a>
    
    <div className="p-5">
        <a href="#">
            <h5 className="mb-2 text-2xl font-bold tracking-tight text-gray-900 dark:text-white">{user?.username}</h5>
        </a>
       
            <div className="mb-2">
                        <label
                            htmlFor="name"
                            className="block text-sm font-semibold text-white-800 float-start"
                        >
                            Name
                        </label>
                        <input
                            id="name"
                           value={user?.name}
                            type="text"
                           {...register('name',{required:true, minLength:3})}
                          
                            onChange={(event)=>{setUser({...user, name: event.target.value }); setValue('name',event.target.value)}}
                            className="block w-full px-4 py-2 mt-2 text-purple-700 bg-white border rounded-md focus:border-purple-400 focus:ring-purple-300 focus:outline-none focus:ring focus:ring-opacity-40"
                        />
                        {formState.errors.name?.type==='required' && <div className="float-start text-orange-500 mb-2">Name field is required</div>}
                        {formState.errors.name?.type==='minLength' && <div className="float-start text-orange-500 mb-2">Name field should be atleast 3 chars.</div>}
        </div>
        <div className="mb-2">
                        <br />
                        <label
                            htmlFor="phone"
                            className="block text-sm font-semibold text-white-800 float-start"
                        >
                            Phone
                        </label>
                        <input
                            id="phone"
                            value={user?.phone}
                            type="text"
                            
                            {...register('phone',{required:true, minLength:10,})}
                            onChange={(event)=>{setUser({...user, phone: event.target.value });setValue('phone',event.target.value);}}
                            className="block w-full px-4 py-2 mt-2 text-purple-700 bg-white border rounded-md focus:border-purple-400 focus:ring-purple-300 focus:outline-none focus:ring focus:ring-opacity-40"
                        />

                        {formState.errors.phone?.type==='required' && <div className="float-start text-orange-500 mb-2">Phone field is required</div>}
                        {formState.errors.phone?.type==='minLength' && <div className="float-start text-orange-500 mb-2">Phone field should be atleast 10 chars.</div>}
                      
        </div>
        <div className="mb-2">
            <br/>
                        <label
                            htmlFor="email"
                            className="block text-sm font-semibold text-white-800 float-start"
                        >
                            EMail
                        </label>
                        <input
                            id="email"
                            value={user?.email}
                            type="email"
                            {...register('email',{required:true, minLength:3})}
                            onChange={(event)=>setUser({...user, email: event.target.value })}
                            className="block w-full px-4 py-2 mt-2 text-purple-700 bg-white border rounded-md focus:border-purple-400 focus:ring-purple-300 focus:outline-none focus:ring focus:ring-opacity-40"
                        />
                        {formState.errors.email?.type==='required' && <div className="float-start text-orange-500 mb-2">Email field is required</div>}
                        {formState.errors.email?.type==='minLength' && <div className="float-start text-orange-500 mb-2">Email field should be atleast 3 chars.</div>}
        </div>
        <div className="mb-2">
            <br/>
                        <label
                            htmlFor="website"
                            className="block text-sm font-semibold text-white-800 float-start"
                        >
                            Website
                        </label>
                        <input
                            id="website"
                            value={user?.website}
                            type="text"
                            {...register('website',{required:true, minLength:3})}
                            onChange={(event)=>setUser({...user, website: event.target.value })}
                            className="block w-full px-4 py-2 mt-2 text-purple-700 bg-white border rounded-md focus:border-purple-400 focus:ring-purple-300 focus:outline-none focus:ring focus:ring-opacity-40"
                            
                        />
                        {formState.errors.website?.type==='required' && <div className="float-start text-orange-500 mb-2">Website field is required</div>}
                        {formState.errors.website?.type==='minLength' && <div className="float-start text-orange-500 mb-2">Website field should be atleast 3 chars.</div>}
        </div>
        <br/>
        
        {isNewForm && <button type="submit" onClick={setFormValue} className="text-white bg-gradient-to-r from-purple-500 via-purple-600 to-purple-700 hover:bg-gradient-to-br focus:ring-4 focus:outline-none focus:ring-purple-300 dark:focus:ring-purple-800 shadow-lg shadow-purple-500/50 dark:shadow-lg dark:shadow-purple-800/80 font-medium rounded-lg text-sm px-5 py-2.5 text-center me-2 mb-2" >CREATE</button>}
        {!isNewForm && <button type="submit" onClick={setFormValue} className="text-white bg-gradient-to-r from-cyan-500 via-cyan-600 to-cyan-700 hover:bg-gradient-to-br focus:ring-4 focus:outline-none focus:ring-cyan-300 dark:focus:ring-cyan-800 shadow-lg shadow-cyan-500/50 dark:shadow-lg dark:shadow-cyan-800/80 font-medium rounded-lg text-sm px-5 py-2.5 text-center me-2 mb-2" >UPDATE</button>}
        <button type="submit" onClick={goList} className="text-white bg-gradient-to-r from-red-500 via-red-600 to-red-700 hover:bg-gradient-to-br focus:ring-4 focus:outline-none focus:ring-red-300 dark:focus:ring-red-800 shadow-lg shadow-red-500/50 dark:shadow-lg dark:shadow-red-800/80 font-medium rounded-lg text-sm px-5 py-2.5 text-center me-2 mb-2" >To List</button>
    </div>
</div>
</center>
</div>
</form>
    );
  }