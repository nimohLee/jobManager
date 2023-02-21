import axios from "axios";
import React, { useEffect, useState } from "react";
import { Spinner } from "react-bootstrap";
import Pagination from "react-js-pagination";
import {
    JobPlanetSearchResult,
    SearchResultData,
} from "../../common/types/propType";
import SearchResult from "./SearchResult";
import SortOptionSelect from "./SortOptionSelect";

function JobSearch() {
    const [jobPostSite, setJobPostSite] = useState<string>();
    const [searchWord, setSearchWord] = useState<string>();
    const [recruitSort, setRecruitSort] = useState<string>();
    const [searchResult, setSearchResult] = useState<SearchResultData>();
    const [jobPlanetResult, setJobPlanetResult] =
        useState<JobPlanetSearchResult>();
    const [recruitPage, setRecruitPage] = useState(1);
    const [loading, setLoding] = useState(false);

    const fetch = async (page: number) => {
        setLoding(true);
        const url = `/api/v1/crawler/${jobPostSite}`;
        let params: object;
        if (jobPostSite === "jobplanet") {
            params = {
                companyName: searchWord,
            };
        } else {
            params = {
                searchWord,
                recruitPage: page,
                recruitSort,
            };
        }
        const result = await axios({
            method: "get",
            url,
            params: params,
        });
        jobPostSite === "jobplanet"
            ? setJobPlanetResult(result.data)
            : setSearchResult(result.data);
        setLoding(false);
    };

    const onChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        setSearchWord(e.target.value);
    };

    const onSelect = (e: React.ChangeEvent<HTMLSelectElement>) => {
        if (e.target.name === "job_post") {
            setJobPostSite(e.target.value);
            const recruitSortSelect = document.querySelector("#recruitSort");
        } else {
            setRecruitSort(e.target.value);
        }
    };

    const onSubmit = (e: React.FormEvent) => {
        e.preventDefault();
        if (!jobPostSite) {
            alert("구직사이트를 선택해주세요");
        } else if (!recruitSort&&jobPostSite !=='jobplanet') {
            alert("정렬옵션을 선택해주세요");
        } else fetch(1);
    };

    const handlePageChange = (page: number) => {
        setRecruitPage(page);
        fetch(page);
    };

    return (
        <div className='mb-32'>
            <form action="" onSubmit={onSubmit}>
                <div className="flex flex-col mx-10 md:flex-row justify-center">
                    <select
                        name="job_post"
                        id=""
                        onChange={onSelect}
                        defaultValue="default"
                        className="px-3 py-2 border border-black"
                    >
                        <option value="default" disabled>
                            구직사이트
                        </option>
                        <option value="saramin">사람인</option>
                        <option value="jobkorea">잡코리아</option>
                        <option value="incruit">인크루트</option>
                        <option value="jobplanet">잡플래닛</option>
                    </select>
                    <input
                        type="text"
                        name="searchPost"
                        id=""
                        placeholder="검색어를 입력하세요"
                        onChange={onChange}
                        required
                        className="py-1.5 px-3 border border-black"
                    />
                    {jobPostSite === "jobplanet" || (
                        <SortOptionSelect
                            jobPostSite={jobPostSite}
                            setRecruitSort={setRecruitSort}
                        />
                    )}
                    <button
                        type="submit"
                        className="py-1.5 px-3 mr-3 bg-blue-900 text-white"
                    >
                        검색
                    </button>
                </div>
            </form>
            {loading ? (
                <div className="flex justify-center items-center h-80">
                    <Spinner animation="border" className="float-center" />
                </div>
            ) : jobPostSite === "jobplanet" ? (
                <div className='border border-black my-10 p-10'>
                    <a href={jobPlanetResult?.companyUrl} className='no-underline text-black font-bold text-lg' target='_blank'>{jobPlanetResult?.companyName}</a>
                    <div>{jobPlanetResult?.titleSub}</div>
                    <div>⭐️</div>
                    <div>{jobPlanetResult?.rate}</div>
                </div>
            ) : (
                searchResult && (
                    <div>
                        <SearchResult searchResult={searchResult} />
                        <Pagination
                            activePage={recruitPage}
                            itemsCountPerPage={searchResult.length}
                            totalItemsCount={searchResult[0].resultCount}
                            pageRangeDisplayed={5}
                            prevPageText={"‹"}
                            nextPageText={"›"}
                            onChange={handlePageChange}
                        />
                    </div>
                )
            )}
        </div>
    );
}

export default JobSearch;
