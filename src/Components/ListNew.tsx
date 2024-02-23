import axios, { CanceledError } from "axios";
import { useContext, useEffect, useState } from "react";
import Spinner from "./Spinner";
import { Navigate, useNavigate } from "react-router-dom";
import {Employee as User} from "../Model/User";
import { AuthContext } from "../Context/Auth";

const stopfetch=()=>{console.log("stop fetching")}

const MyList=()=>{
  const { isLoggedIn, token, userName } = useContext(AuthContext);
    const navigate = useNavigate();
    const repoLink="https://jsonplaceholder.typicode.com/users";
    const controller = new AbortController();
    let src:string="https://img.freepik.com/free-vector/mysterious-gangster-character-illustration_23-";
    const [users, setUsers]=useState<User[]>([]);
    const[error, setError]=useState('');
    const[isLoading, setLoading]=useState(false);
    const deleteUser=(id:number)=>{
        const originalUsers=[...users];
        setUsers(users.filter(user=>user.id!==id));
        axios.delete(repoLink+"/"+id, {
          headers: { 'Authorization': + token }
          })
        .catch(err=>{
            setError(err.message);setUsers(originalUsers);
        });
    }
    const goDetail=(id:number)=>{
        navigate('/reactdemouser/detail/'+id);
        
    }
    const goCreate=()=>{
        navigate('/reactdemouser/detail/new');
        
    }
    useEffect(()=>{
        
        setLoading(true);
        axios.get<User[]>(repoLink,{signal:controller.signal,
          headers: { 'Authorization': + token }
          })
        .then(response=>{
            console.log(response.data);
            setUsers(response.data);
        })
        .catch(err=>{console.log(err); 
            if(err instanceof CanceledError) return;
            setError(err.message)
        }).finally(()=>{setLoading(false);});
        
       //return ()=>controller.abort(); 
    },[]);
    return (<>
        
        <div
            
            className='bg-gray-800 w-full text-white text-center md:text-left'
          >
            <div className='max-w-screen-lg p-4 mx-auto flex flex-col justify-center w-full h-full'>
              <div className='pb-8 m-8'>
                <p className='text-4xl font-bold inline border-b-4 border-gray-500'>
    Title            </p>
                <p className='py-6'>subtitle</p>
                {error && <div className="float-start"><p className="text-orange-600">{error}</p></div>}

                <div className="float-end text-white">
                <button type="button" className="text-white bg-gradient-to-r from-purple-500 via-purple-600 to-purple-700 hover:bg-gradient-to-br focus:ring-4 focus:outline-none focus:ring-purple-300 dark:focus:ring-purple-800 shadow-lg shadow-purple-500/50 dark:shadow-lg dark:shadow-purple-800/80 font-medium rounded-lg text-sm px-5 py-2.5 text-center me-2 mb-2" onClick={goCreate}>CREATE</button>

                </div>
                {isLoading && <Spinner />}


              </div>
    
              <div className='grid sm:grid-cols-2 md:grid-cols-3 gap-8 sm:px-5'>
                {users.map((user) => (
                
                  <div
                    key={user.id}
                    className='shadow-md shadow-gray-600 rounded-lg overflow-hidden'
                  >
                    <img
                      src={src+(user.id)+".jpg"}
                      alt=''
                      
                      className='rounded-md duration-200  hover:scale-105'
                    />
                    
                    <div className="p-2">
        <a href="#">
            <h5 className="mb-2 text-2xl font-bold tracking-tight text-white dark:text-white">{user.name}</h5>
        </a>
        <hr className="p5 mb-5"/>
        
        <p className="mb-3 font-normal text-white dark:text-gray-400"> {user.email}</p>
        <p className="mb-3 font-normal text-white dark:text-gray-400"> {user.phone}</p>
        <p className="mb-3 font-normal text-white dark:text-gray-400"> {user.username}</p>
    </div>
                    <div className='flex items-center justify-center'>
                      <button
                        className='w-1/2 px-6 py-3 m-4 bg-cyan-500 duration-200 hover:bg-cyan-600'
                        onClick={() =>goDetail(user.id)}
                      >
                        Update
                      </button>
                     
                      <button
                        className='w-1/2 px-6 py-3 m-4 bg-pink-500 duration-200 hover:bg-pink-600'
                        onClick={() => deleteUser(user.id)}
                      >
                        Delete
                      </button>
                    </div>
                  </div>
                ))}
              </div>
              </div>
          </div>
            
    </>)
}
export default MyList;