package com.nimoh.jobManager.data.dto.job;

import com.nimoh.jobManager.commons.enums.JobSearchSite;
import com.nimoh.jobManager.commons.enums.RequiredExperience;
import com.nimoh.jobManager.commons.enums.Result;
import lombok.Data;

@Data
public class JobSearchCondition {

    private String name;
    private Result result;
    private RequiredExperience requiredExperience;
    private int minSalary;
    private int maxSalary;
    private String location;
    private JobSearchSite jobSearchSite;

    public JobSearchCondition(){}

    public JobSearchCondition(String name, Result result, RequiredExperience requiredExperience, int minSalary, int maxSalary, JobSearchSite jobSearchSite) {
        this.name = name;
        this.result = result;
        this.requiredExperience = requiredExperience;
        this.minSalary = minSalary;
        this.maxSalary =  maxSalary;
        this.jobSearchSite = jobSearchSite;
    }
}