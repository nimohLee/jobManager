import axios, { AxiosResponse } from "axios";
import React, { SelectHTMLAttributes, useEffect, useState } from "react";
import { SearchResultData } from '../../common/types/propType';
import SearchResult from "./SearchResult";

function JobSearch() {
    const [jobPostSite, setJobPostSite] = useState<string>();
    const [searchWord, setSearchWord] = useState<string>();
    const [recruitSort, setRecruitSort] = useState<string>();
    const [searchResult, setSearchResult] = useState<SearchResultData>();

    const fetch = async () => {
        const url = `/api/v1/crawler/${jobPostSite}`;
        const result = await axios({
            method: "get",
            url,
            params: {
                searchWord,
                recruitPage: "1",
                recruitSort,
            },
        });
        console.log(result.data);
        setSearchResult(result.data);
    };

    const onChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        setSearchWord(e.target.value);
    };

    const onSelect = (e: React.ChangeEvent<HTMLSelectElement>) => {
        if (e.target.name === "job_post"){
            setJobPostSite(e.target.value);
        }else{
            setRecruitSort(e.target.value);
        }
    };

    const onSubmit = (e: React.FormEvent) => {
        e.preventDefault();
        if(!jobPostSite){
            alert("구직사이트를 선택해주세요");
        }else if(!recruitSort){
            alert("정렬옵션을 선택해주세요");
        }else fetch();
    };
    return (
        <div>
            <form action="" onSubmit={onSubmit}>
                <div>
                    <select name="job_post" id="" onChange={onSelect} defaultValue='default'>
                        <option value="default" disabled>구직사이트</option>
                        <option value="saramin">사람인</option>
                        <option value="jobkorea">잡코리아</option>
                        <option value="incruit">인크루트</option>
                        <option value="jumbit">점핏</option>
                    </select>
                    <input
                        type="text"
                        name="searchPost"
                        id=""
                        placeholder="검색어를 입력하세요"
                        onChange={onChange}
                        required
                    />
                    {jobPostSite === "saramin" && (
                        <select name="recruit_sort" id="" onChange={onSelect} defaultValue='default'>
                            <option value="default" disabled>정렬옵션</option>
                            <option value="relation">관련도순</option>
                            <option value="accuracy">정확도순</option>
                            <option value="reg_dt">등록일순</option>
                            <option value="edit_dt">수정일순</option>
                            <option value="closing_dt">마감일순</option>
                            <option value="apply_cnt">지원자순</option>
                            <option value="employ_cnt">사원수순</option>
                        </select>
                    )}
                </div>
                <button type="submit">검색</button>
            </form>
            {searchResult ? <SearchResult searchResult={searchResult}/> : <div>검색 해주세요</div>}
        </div>
    );
}

export default JobSearch;
