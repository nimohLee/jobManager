import React from "react";
import Application from "../manager/Application";
import { useEffect, useState } from "react";
import axios from "axios";
import { ResponseInfo } from "../../common/types/propType";
import { getCookie } from '../../common/functions/cookie';

type JobData = [ResponseInfo["info"]];

function Manager() {
    const [jobDatas, setJobDatas] = useState<JobData>();

    const fetchJobs = async () => {
        const url = "api/v1/job";
        const accessToken = getCookie("accessToken");
        if (accessToken){
            try {
                const result = await axios({
                    method: "get",
                    url: url,
                    headers : {
                        "Authorization" : `Bearer ${accessToken}`
                    }
                });
                setJobDatas(result.data);
            } catch (err) {
                console.error(err);
            }
        } else {
            alert("로그인이 필요합니다.")
            window.location.href = "/";
        }
        
    };

    useEffect(() => {
        fetchJobs();
    }, []);

    return (
        <div>
            {!jobDatas ? (
                <div className="text-center mb-32 text-xl">
                    지원 내역이 없습니다.
                </div>
            ) : (
                jobDatas?.map((jobData) => {
                    return (
                        <div key={jobData.id}>
                            <Application info={jobData} />
                            <hr />
                        </div>
                    );
                })
            )}
        </div>
    );
}

export default Manager;
