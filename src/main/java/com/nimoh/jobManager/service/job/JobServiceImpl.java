package com.nimoh.jobManager.service.job;

import com.nimoh.jobManager.commons.user.UserErrorResult;
import com.nimoh.jobManager.commons.user.UserException;
import com.nimoh.jobManager.data.dto.job.JobResponse;
import com.nimoh.jobManager.data.dto.job.JobRequest;
import com.nimoh.jobManager.commons.job.JobErrorResult;
import com.nimoh.jobManager.commons.job.JobException;
import com.nimoh.jobManager.data.entity.Job;
import com.nimoh.jobManager.data.entity.User;
import com.nimoh.jobManager.repository.JobRepository;
import com.nimoh.jobManager.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 직무 지원 서비스 구현체
 * @author nimoh
 * */
@Service
public class JobServiceImpl implements JobService {

    private final JobRepository jobRepository;
    private final UserRepository userRepository;

    @Autowired
    public JobServiceImpl(JobRepository jobRepository,
                          UserRepository userRepository){
        this.jobRepository = jobRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<JobResponse> findByUser(Long userId) {
        Optional<User> user = userRepository.findById(userId);
        if(user.isEmpty()){
            throw new UserException(UserErrorResult.USER_NOT_FOUND);
        }
        final List<Job> findResult = jobRepository.findAllByUser(user.get());
        return findResult.stream().map(this::makeJobResponse)
                .collect(Collectors.toList());
    }

    @Override
    public JobResponse save(JobRequest jobRequest, Long userId) {
        System.out.println(jobRequest);
        final Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()){
            throw new JobException(JobErrorResult.REQUEST_VALUE_INVALID);
        }
        final Job job = makeJob(jobRequest, user.get());
        final Job savedJob = jobRepository.save(job);

        return makeJobResponse(savedJob);
    }

    public JobResponse update(JobRequest jobRequest, Long userId, Long jobId){
        final Optional<Job> targetJob = jobRepository.findById(jobId);
        if(targetJob.isEmpty()){
            throw new JobException(JobErrorResult.APPLY_NOT_FOUND);
        }
        Optional<User> user = userRepository.findById(userId);
        if(!targetJob.get().getUser().equals(user.get())){
            throw new JobException(JobErrorResult.NO_PERMISSION);
        }

        Job job = makeJob(jobRequest, user.get());
        Job result = jobRepository.save(job);

        return makeJobResponse(result);
    }

    public boolean delete(Long jobId,Long userId) {
        final Optional<Job> targetJob = jobRepository.findById(jobId);
        final Optional<User> user = userRepository.findById(userId);
        if (targetJob.isEmpty()){
            throw new JobException(JobErrorResult.APPLY_NOT_FOUND);
        }
        if (user.isEmpty()){
            throw new JobException(JobErrorResult.REQUEST_VALUE_INVALID);
        }
        if(!targetJob.get().getUser().equals(user.get())){
            throw new JobException(JobErrorResult.NO_PERMISSION);
        }
        jobRepository.deleteById(jobId);
        return true;
    }

    private Job makeJob(JobRequest jobRequest, User user) {
        return Job.builder()
                .companyName(jobRequest.getCompanyName())
                .huntingSite(jobRequest.getHuntingSite())
                .salary(jobRequest.getSalary())
                .link(jobRequest.getLink())
                .employeesNumber(jobRequest.getEmployeesNumber())
                .location(jobRequest.getLocation())
                .x(jobRequest.getX())
                .y(jobRequest.getY())
                .position(jobRequest.getPosition())
                .requiredCareer(jobRequest.getRequiredCareer())
                .primarySkill(jobRequest.getPrimarySkill())
                .applyDate(jobRequest.getApplyDate())
                .result("지원완료")
                .note(jobRequest.getNote())
                .user(user)
                .build();
    }

    private JobResponse makeJobResponse(Job job) {
        return JobResponse.builder()
                .id(job.getId())
                .companyName(job.getCompanyName())
                .huntingSite(job.getHuntingSite())
                .salary(job.getSalary())
                .link(job.getLink())
                .employeesNumber(job.getEmployeesNumber())
                .location(job.getLocation())
                .x(job.getX())
                .y(job.getY())
                .position(job.getPosition())
                .requiredCareer(job.getRequiredCareer())
                .primarySkill(job.getPrimarySkill())
                .applyDate(job.getApplyDate())
                .result(job.getResult())
                .note(job.getNote())
                .build();
    }
}