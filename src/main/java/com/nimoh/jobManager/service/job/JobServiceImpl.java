package com.nimoh.jobManager.service.job;

import com.nimoh.jobManager.exception.user.UserErrorResult;
import com.nimoh.jobManager.exception.user.UserException;
import com.nimoh.jobManager.data.dto.job.JobResponse;
import com.nimoh.jobManager.data.dto.job.JobRequest;
import com.nimoh.jobManager.exception.job.JobErrorResult;
import com.nimoh.jobManager.exception.job.JobException;
import com.nimoh.jobManager.data.entity.Job;
import com.nimoh.jobManager.data.entity.User;
import com.nimoh.jobManager.repository.job.JobRepository;
import com.nimoh.jobManager.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 직무 지원 서비스 구현체
 *
 * @author nimoh
 */
@Service
@Transactional
@RequiredArgsConstructor
public class JobServiceImpl implements JobService {

    private final JobRepository jobRepository;
    private final UserRepository userRepository;

    /**
     * 현재 유저의 지원 내역 모두 조회
     *
     * @param userId 현재 유저 PK
     * @return 조회된 지원 내역 리스트 반환
     * @throws UserException 해당 유저가 없을 시 예외 발생
     */
    @Override
    public List<JobResponse> findByUser(Long userId) {
        final User user = userRepository.findById(userId).orElseThrow(()-> new UserException(UserErrorResult.USER_NOT_FOUND));
        final List<Job> findResult = jobRepository.findAllByUserOrderByApplyDateDesc(user);
        return findResult.stream().map(this::makeJobResponse)
                .collect(Collectors.toList());
    }

    /**
     * 지원내역 등록
     *
     * @param jobRequest 등록할 지원 내역 DTO
     * @param userId 현재 유저 PK
     * @return 저장된 지원 내역 DTO
     * @throws UserException 해당 유저가 없을 시 예외 발생
     */
    @Override
    public JobResponse save(JobRequest jobRequest, Long userId) {
        final User user = userRepository.findById(userId).orElseThrow(()-> new UserException(UserErrorResult.USER_NOT_FOUND));
        final Job job = makeJob(jobRequest, user);
        try {
            final Job savedJob = jobRepository.save(job);
            return makeJobResponse(savedJob);
        } catch (Exception e) {
            throw new JobException(JobErrorResult.REQUEST_VALUE_INVALID);
        }
    }

    /**
     * 지원 내역 수정
     *
     * @param jobRequest 수정할 지원 내역 DTO
     * @param userId 현재 유저 PK
     * @param jobId 수정할 지원 내역 PK
     * @return 수정한 지원 내역 Response
     * @throws JobException 지원 내역을 찾을 수 없는 경우 또는 지원 내역에 해당하는 유저가 아닌 경우
     * @throws UserException 현재 유저가 DB에 없을 때 예외 발생
     */
    public JobResponse update(JobRequest jobRequest, Long userId, Long jobId) {
        final Job targetJob = jobRepository.findById(jobId).orElseThrow(()-> new JobException(JobErrorResult.APPLY_NOT_FOUND));
        User user = userRepository.findById(userId).orElseThrow(()-> new UserException(UserErrorResult.USER_NOT_FOUND));
        if (!targetJob.getUser().equals(user)) {
            throw new JobException(JobErrorResult.NO_PERMISSION);
        }

        Job job = makeJob(jobRequest, user);
        job.setId(jobId);
        Job result = jobRepository.save(job);

        return makeJobResponse(result);
    }

    /**
     * 지원 내역 삭제
     *
     * @param jobId 삭제 하고자 하는 지원 내역 PK
     * @param userId 현재 유저 PK
     * @return 삭제 성공 시 true 반환
     * @throws JobException 지원 내역을 찾을 수 없는 경우 또는 지원 내역에 해당하는 유저가 아닌 경우
     * @throws UserException 현재 유저가 DB에 없을 때 예외 발생
     */
    public boolean delete(Long jobId, Long userId) {
        final Job targetJob = jobRepository.findById(jobId).orElseThrow(()-> new JobException(JobErrorResult.APPLY_NOT_FOUND));
        final User user = userRepository.findById(userId).orElseThrow(()-> new JobException(JobErrorResult.REQUEST_VALUE_INVALID));

        if (!targetJob.getUser().equals(user)) {
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
                .applyDate(jobRequest.getApplyDate())
                .result("지원완료")
                .note(jobRequest.getNote())
                .primarySkill(jobRequest.getPrimarySkill())
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
                .applyDate(job.getApplyDate())
                .result(job.getResult())
                .note(job.getNote())
                .build();
    }
}