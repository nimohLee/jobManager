package com.nimoh.jobManager.data.dto.job;

import com.nimoh.jobManager.commons.enums.JobSearchSite;
import com.nimoh.jobManager.commons.enums.RequiredExperience;
import com.nimoh.jobManager.commons.enums.Result;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.lang.Nullable;

@Data
@Builder
@AllArgsConstructor
public class JobSearchCondition {

    @Nullable
    private String name;
    @Nullable
    private Result result;
    @Nullable
    private RequiredExperience requiredExperience;
    @Nullable
    private Integer minSalary;
    @Nullable
    private Integer maxSalary;
    @Nullable
    private String location;
    @Nullable
    private JobSearchSite jobSearchSite;

    public JobSearchCondition(){}

    public JobSearchCondition(String name, Result result, RequiredExperience requiredExperience, Integer minSalary, Integer maxSalary, JobSearchSite jobSearchSite) {
        this.name = name;
        this.result = result;
        this.requiredExperience = requiredExperience;
        this.minSalary = minSalary;
        this.maxSalary =  maxSalary;
        this.jobSearchSite = jobSearchSite;
    }
}