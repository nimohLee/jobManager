import { useState, useEffect } from 'react';
import { IncruitOption, JobKoreaOption, SaraminOption } from "./SortOption";

interface PropsInfo {
    jobPostSite: string|undefined;
    setRecruitSort: React.Dispatch<React.SetStateAction<string | undefined>>;
}

function SortOptionSelect(props: PropsInfo) {
    const [jobPostSite,setJobPostSite] = useState<SaraminOption|JobKoreaOption|IncruitOption>(props.jobPostSite === "saramin"
    ? SaraminOption.getInstance()
    : props.jobPostSite === "jobkorea"
    ? JobKoreaOption.getInstance()
    : IncruitOption.getInstance());

    useEffect(()=>{
      fetchJobPostSite();
    },[props.jobPostSite]);

  useEffect(()=>{
    const selectedValue = document.querySelector<HTMLSelectElement>("#recruitSort")?.value;
    props.setRecruitSort(selectedValue);
  },[jobPostSite])

  const fetchJobPostSite = () => {
    setJobPostSite(props.jobPostSite === "saramin"
    ? SaraminOption.getInstance()
    : props.jobPostSite === "jobkorea"
    ? JobKoreaOption.getInstance()
    : IncruitOption.getInstance());
  }

  const onChange = (e: React.ChangeEvent<HTMLSelectElement>) => {
    props.setRecruitSort(e.target.value);
  }

    return (
        <>
            <select
                name="recruit_sort"
                id="recruitSort"
                onChange={onChange}
                defaultValue="default"
                className="py-2 px-3 border border-black"
            >
                <option value="default" disabled>
                    정렬옵션
                </option>
                <option value={jobPostSite.accurancy}>정확도순</option>
                <option value={jobPostSite.regDate}>등록일순</option>
                <option value={jobPostSite.editDate}>수정일순</option>
                <option value={jobPostSite.closingDate}>마감일순</option>
                <option value={jobPostSite.applyCount}>지원자순</option>
                {
                  jobPostSite instanceof SaraminOption?
                  <><option value="relation">관련도순</option>
                  <option value={jobPostSite.employCount}>사원수순</option>
                  </>
                  :<option value={jobPostSite.readCount}>조회수순</option>
                }
            </select>
        </>
    );
}

export default SortOptionSelect;
