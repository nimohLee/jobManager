import Button from "react-bootstrap/Button";
import axios from 'axios';
import { FormEvent, useState } from 'react';
import styled from 'styled-components';

interface CustomError extends Error {
    response?: {
       data: any;
       status: number;
       headers: string;
    };
 }

 interface InputValue{
    value?: string,
    error?: boolean
 }

function SignUp() {

    const ErrorMessage = styled.button`
        color: red;    
        font-size: 0.9em;
    `

    const [id, setId] = useState<InputValue>();
    const [name, setName] = useState<InputValue>();
    const [password, setPassword] = useState<InputValue>();
    const [email, setEmail] = useState<InputValue>();

    const onChange = (e: React.FormEvent<HTMLInputElement>): void => {
        const input = e.currentTarget;
        switch(input.name){
            case 'id':
                setId({value: input.value, error: false});
                break;
            case 'name':
                setName({value: input.value, error: false});    
                break;
            case 'password':
                setPassword({value: input.value, error: false});
                break;
            case 'email':
                setEmail({value: input.value, error: false});
                break;
            default:
                console.error("Invalid input error");
        }
    }

    const postSignUp = async (e:FormEvent<HTMLFormElement>) =>{
        e.preventDefault();
        const url = "http://localhost:8000/api/v1/user";
        const data = {
            uid : id?.value?.trim(),
            password : password?.value?.trim(),
            email : email?.value?.trim(),
            name: name?.value?.trim()
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
            alert("회원가입이 완료되었습니다.");
            window.location.href="/";
        }catch(err){
            // AxiosError에서 data에 접근할 수 없어 커스텀 에러 참조
            const error = err as CustomError;
            if(error.response?.status === 409){
                alert("이미 존재하는 아이디 입니다.");
            }
            else if(error.response?.data.message.match('id')){
                setId({error:true});
            }
            if(error.response?.data.message.match('password')){
                setPassword({error:true});
            }
        }
    }

    return (
        <main className='flex justify-center'>
            <section className='py-16 px-32 top-0 relative border border-b'>
            <form onSubmit={postSignUp}>
                <div className="mb-3 flex flex-column">
                    <label className='mb-2'>이름</label>
                    <input type="Name" name='name' placeholder="이름을 입력하세요"  className='px-3 py-2 border border-solid' onChange={onChange}/>
                </div>

                <div className="mb-3 flex flex-column">
                    <label className='mb-2'>아이디</label>
                    <input type="ID" name ='id' placeholder="아이디를 입력하세요"  className='px-3 py-2 border border-solid' onChange={onChange}/>
                    {
                        id?.error&&<ErrorMessage>아이디는 6자 이상, 10자 이하의 영문자로 입력해주세요</ErrorMessage>
                    }
                </div>

                <div className="mb-3 flex flex-column">
                    <label className='mb-2'>비밀번호</label>
                    <input type="Password" name='password' placeholder="비밀번호를 입력하세요"  className='px-3 py-2 border border-solid' onChange={onChange}/>
                    {
                        password?.error&&<ErrorMessage>비밀번호는 8자 이상, 12자 이하의 영문자로 입력해주세요</ErrorMessage>
                    }
                </div>

                <div className="mb-3 flex flex-column">
                    <label className='mb-2'>이메일</label>
                    <input type="Email" name='email' placeholder="이메일을 입력하세요"  className='px-3 py-2 border border-solid' onChange={onChange}/>
                </div>

                <Button variant="secondary" type="submit" className='float-right'>
                    회원가입
                </Button>
            </form>
            </section>
        </main>
    );
}

export default SignUp;
