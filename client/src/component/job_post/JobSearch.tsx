import axios from "axios";
import React, { useState } from "react";
import { Spinner } from "react-bootstrap";
import Pagination from "react-js-pagination";
import { SearchResultData } from "../../common/types/propType";
import SearchResult from "./SearchResult";

function JobSearch() {
    const [jobPostSite, setJobPostSite] = useState<string>();
    const [searchWord, setSearchWord] = useState<string>();
    const [recruitSort, setRecruitSort] = useState<string>();
    const [searchResult, setSearchResult] = useState<SearchResultData>();
    const [recruitPage, setRecruitPage] = useState(1);
    const [loading, setLoding] = useState(false);

    const fetch = async (page: number) => {
        setLoding(true);
        const url = `/api/v1/crawler/${jobPostSite}`;
        const result = await axios({
            method: "get",
            url,
            params: {
                searchWord,
                recruitPage: page,
                recruitSort,
            },
        });
        setSearchResult(result.data);
        setLoding(false);
    };

    const onChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        setSearchWord(e.target.value);
    };

    const onSelect = (e: React.ChangeEvent<HTMLSelectElement>) => {
        if (e.target.name === "job_post") {
            setJobPostSite(e.target.value);
        } else {
            setRecruitSort(e.target.value);
        }
    };

    const onSubmit = (e: React.FormEvent) => {
        e.preventDefault();
        if (!jobPostSite) {
            alert("구직사이트를 선택해주세요");
        } else if (!recruitSort) {
            alert("정렬옵션을 선택해주세요");
        } else fetch(1);
    };

    const handlePageChange = (page: number) => {
        setRecruitPage(page);
        fetch(page);
    };

    return (
        <div>
            <form action="" onSubmit={onSubmit}>
                <div className="flex justify-center">
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
                        <option value="jumbit">점핏</option>
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
                    {jobPostSite === "saramin" && (
                        <select
                            name="recruit_sort"
                            id=""
                            onChange={onSelect}
                            defaultValue="default"
                            className="py-2 px-3 border border-black"
                        >
                            <option value="default" disabled>
                                정렬옵션
                            </option>
                            <option value="relation">관련도순</option>
                            <option value="accuracy">정확도순</option>
                            <option value="reg_dt">등록일순</option>
                            <option value="edit_dt">수정일순</option>
                            <option value="closing_dt">마감일순</option>
                            <option value="apply_cnt">지원자순</option>
                            <option value="employ_cnt">사원수순</option>
                        </select>
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
            ) : searchResult ? (
                <div>
                    <SearchResult searchResult={searchResult} />
                    <Pagination
                        activePage={recruitPage}
                        itemsCountPerPage={20}
                        totalItemsCount={searchResult[0].resultCount}
                        pageRangeDisplayed={5}
                        prevPageText={"‹"}
                        nextPageText={"›"}
                        onChange={handlePageChange}
                    />
                </div>
            ) : (
                <div className="text-center">검색 해주세요</div>
            )}
        </div>
    );
}

export default JobSearch;
