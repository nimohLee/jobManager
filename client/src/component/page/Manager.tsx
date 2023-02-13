import React from "react";
import Application from "../manager/Application";
import { useEffect, useState } from "react";
import axios from "axios";
import { ResponseInfo } from "../../common/types/propType";

type JobData = [ResponseInfo["info"]];

function Manager() {
    const [jobDatas, setJobDatas] = useState<JobData>();

    const fetchJobs = async () => {
        const url = "api/v1/job";
        try {
            const result = await axios({
                method: "get",
                url: url,
            });
            setJobDatas(result.data);
        } catch (err) {
            console.error(err);
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
