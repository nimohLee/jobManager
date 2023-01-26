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

    const postLogin = async (e:FormEvent<HTMLFormElement>) =>{
        e.preventDefault();
        const url = "/api/v1/user/login";
        const data = {
            uid : id?.trim(),
            password : password?.trim()
        }
        try{
            await axios({
                method: "post",
                url: url,
                data: data,
                headers : {
                    "Content-Type" : 'application/json'
                }
            });
            localStorage.setItem("isLogin","true");
            window.location.href="/";
        }catch(err){
            alert("아이디 또는 비밀번호가 잘못되었습니다");
        }
        
    }

    return (
        <main className='flex justify-center'>
            <section className='py-16 px-32 top-20 relative border border-b'>
            <form onSubmit={postLogin}>
                <div className="mb-3 flex flex-column">
                    <label className='mb-2'>아이디</label>
                    <input type="ID" placeholder="Enter ID"  className='px-3 py-2 border border-solid' onChange={onChange}/>
                </div>

                <div className="mb-3 flex flex-column">
                    <label className='mb-2'>비밀번호</label>
                    <input type="Password" placeholder="Password"  className='px-3 py-2 border border-solid' onChange={onChange}/>
                </div>

                <Button variant="secondary" type="submit" className='float-right'>
                    로그인
                </Button>
            </form>
            </section>
        </main>
    );
}

export default Login;
