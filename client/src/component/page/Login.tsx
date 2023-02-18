import Button from "react-bootstrap/Button";
import axios from 'axios';
import { FormEvent, useState } from 'react';
import { useEffect } from 'react';
import { Cookies } from 'react-cookie';

function Login() {
    const [id, setId] = useState<string>();
    const [password, setPassword] = useState<string>();
    const cookie = new Cookies();
    const accessCookie = cookie.get("accessCookie");

    useEffect(()=>{
        if(accessCookie){
            alert("이미 로그인된 상태입니다");
            window.location.href = "/";
        }
    },[])

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
            <section className='py-16 px-32 top-0 relative border border-b flex flex-column'>
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
            <a href="/signUp" className='text-neutral-400 mt-3'>아직 회원이 아니신가요?</a>
            </section>
        </main>
    );
}

export default Login;
