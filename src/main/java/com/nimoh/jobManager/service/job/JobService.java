package com.nimoh.jobManager.service.job;

import com.nimoh.jobManager.data.dto.job.JobResponse;
import com.nimoh.jobManager.data.dto.job.JobRequest;
import com.nimoh.jobManager.data.dto.job.JobSearchCondition;

import java.util.List;

public interface JobService {

    List<JobResponse> findByUser(Long userId);

    List<JobResponse> findByCond(Long userId, JobSearchCondition cond);
    JobResponse save(JobRequest boardRequest, Long userId);
    JobResponse update(JobRequest boardRequest, Long userId, Long boardId);

    boolean delete(Long boardId,Long userId);
}