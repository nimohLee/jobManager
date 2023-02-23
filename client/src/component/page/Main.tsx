import axios from 'axios';
import { useEffect } from 'react';
import { getCookie } from '../../common/functions/cookie';

function Main() {
    const accessTokenValidCheck = async () => {
        const accessToken = getCookie("accessToken");
        try{
            const result = await axios({
                method: "get",
                url: "/api/v1/token",
                headers:{   
                    "Authorization": `Bearer ${accessToken}`
                }
            })
            if( result.data === false){
                alert("만료된 토큰입니다. 다시 로그인해주세요");
                localStorage.removeItem("isLogin");
                window.location.href = "/login";
            }
            
        }catch(e){
            console.error(e);
        }
    }

    useEffect(()=>{
        localStorage.getItem("isLogin") && accessTokenValidCheck();
    },[])
    return (
        <main className="bg flex">
            <section className="intro-section">
                <div className="ml-10">
                    <h2 className="text-4xl md:text-6xl flex flex-column md:flex-row"><span>Job</span> <span>Manager</span></h2>
                    <p className="text-xl">
                        Manage your application efficiently
                    </p>
                </div>
            </section>
        </main>
    );
}
export default Main;
