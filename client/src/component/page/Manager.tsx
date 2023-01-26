import React from 'react'
import Application from '../manager/Application';
import { useEffect, useState } from 'react';
import axios from 'axios';
import { UpdateInfo } from '../../common/types/propType';

type JobData = [
  UpdateInfo["info"]
];

function Manager() {
  const [jobDatas, setJobDatas] = useState<JobData>();

  const fetchJobs = async () => {
    const url = "api/v1/job";
    try{
      const result = await axios({
        method : "get",
        url: url
      });
      console.log(result.data);
      setJobDatas(result.data);
    }catch(err){
      console.error(err);
    }
  }

  useEffect(()=>{
    fetchJobs();
  },[]);
  
  return (
    <div>
          { jobDatas?.map((jobData)=>{
         return(
          <div key={jobData.id}>
            <Application info={jobData}/>
            <hr/>
          </div>
         )
          })
          }
    </div>
  )
}

export default Manager;