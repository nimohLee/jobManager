package com.nimoh.jobManager.service.job;

import com.nimoh.jobManager.data.dto.job.JobResponse;
import com.nimoh.jobManager.data.dto.job.JobRequest;

import java.util.List;

public interface JobService {

    List<JobResponse> findAll();
    JobResponse save(JobRequest boardRequest, Long userId);
    JobResponse update(JobRequest boardRequest, Long userId, Long boardId);

    boolean delete(Long boardId,Long userId);
}