package com.nimoh.jobManager.repository.job;

import com.nimoh.jobManager.data.dto.job.JobSearchCondition;
import com.nimoh.jobManager.data.entity.Job;

import java.util.List;

public interface JobRepositoryCustom {
    List<Job> findByCond(JobSearchCondition cond);
}
