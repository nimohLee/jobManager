import Button from "react-bootstrap/Button";
import Form from "react-bootstrap/Form";
import axios from 'axios';
import { FormEvent, useState } from 'react';
function Login() {
    const [id, setId] = useState<string>();
    const [password, setPassword] = useState<string>();

    const onChange = (e: React.FormEvent<HTMLInputElement>): void => {
        if(e.currentTarget.type === "text"){
            setId(e.currentTarget.value);
        }else{
            setPassword(e.currentTarget.value);
        }
    }

    const postLogin = (e:FormEvent<HTMLFormElement>) =>{
        e.preventDefault();
        const url = "http://localhost:8000/api/v1/user/login";
        const data = {
            uid : id?.trim(),
            password : password?.trim()
        }
        console.log(data);
        axios({
            method: "post",
            url: url,
            data: data,
            headers : {
                "Content-Type" : 'application/json'
            }
        });
    }

    return (
        <main className='flex justify-center'>
            <section className='py-16 px-32 top-20 relative border border-b'>
            <form onSubmit={postLogin}>
                <div className="mb-3 flex flex-column">
                    <label className='mb-2'>User ID</label>
                    <input type="ID" placeholder="Enter ID"  className='px-3 py-2 border border-solid' onChange={onChange}/>
                </div>

                <div className="mb-3 flex flex-column">
                    <label className='mb-2'>Password</label>
                    <input type="Password" placeholder="Password"  className='px-3 py-2 border border-solid' onChange={onChange}/>
                </div>

                <Button variant="secondary" type="submit" className='float-right'>
                    Login
                </Button>
            </form>
            </section>
        </main>
    );
}

export default Login;
