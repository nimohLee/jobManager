package com.nimoh.jobManager.service;

import com.nimoh.jobManager.data.dto.job.JobResponse;
import com.nimoh.jobManager.data.dto.job.JobRequest;
import com.nimoh.jobManager.data.dto.job.JobSearchCondition;
import com.nimoh.jobManager.exception.job.JobErrorResult;
import com.nimoh.jobManager.exception.job.JobException;
import com.nimoh.jobManager.data.entity.Job;
import com.nimoh.jobManager.data.entity.Skill;
import com.nimoh.jobManager.data.entity.User;
import com.nimoh.jobManager.repository.job.JobRepository;
import com.nimoh.jobManager.repository.UserRepository;
import com.nimoh.jobManager.repository.job.JobRepositoryCustom;
import com.nimoh.jobManager.service.job.JobServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class JobServiceTest {

    @InjectMocks
    private JobServiceImpl jobService;

    @Mock
    private JobRepositoryCustom jobRepositoryCustom;

    @Mock
    private JobRepository jobRepository;

    @Mock
    private UserRepository userRepository;

    @Test
    public void 요청에null값이있어서추가실패() {
        //given
        JobRequest jobRequest = JobRequest.builder().build();
        Long userId = 1L;
        doReturn(Optional.ofNullable(User.builder().id(userId).build())).when(userRepository).findById(any());
        //when
        final JobException result = assertThrows(JobException.class, () -> jobService.save(jobRequest, userId));

        //then
        assertThat(result.getErrorResult()).isEqualTo(JobErrorResult.REQUEST_VALUE_INVALID);
    }

    @Test
    public void 지원추가성공() {
        //given
        Long userId = 1L;
        doReturn(Optional.of(user(1L))).when(userRepository).findById(any());
        doReturn(job(2L)).when(jobRepository).save(any());
        //when
        JobResponse result = jobService.save(jobRequest(), userId);
        //then
        assertThat(result.getCompanyName()).isEqualTo("test");
    }

    @Test
    public void 해당지원이존재하지않는경우삭제실패() {
        //given
        final Long jobId = -1L;
        final Long userId = 1L;
        //when
        final JobException result = assertThrows(JobException.class, () -> jobService.delete(jobId, userId));
        //then
        assertThat(result.getErrorResult()).isEqualTo(JobErrorResult.APPLY_NOT_FOUND);
    }

    @Test
    public void 지원삭제실패_해당게시글ID와현재유저ID가다름() {
        //given
        final Long jobId = 1L;
        final Long userId = 2L;
        doReturn(Optional.of(user(2L))).when(userRepository).findById(any());
        doReturn(Optional.of(job(1L))).when(jobRepository).findById(jobId);
        //when
        final JobException result = assertThrows(JobException.class, () -> jobService.delete(jobId, userId));
        //then
        assertThat(result.getErrorResult()).isEqualTo(JobErrorResult.NO_PERMISSION);
    }

    @Test
    public void 지원삭제성공() {
        //given
        final Long jobId = 2L;
        final User user = user(1L);
        final Long userId = 1L;
        final Job job = job(2L);
        doReturn(Optional.of(user)).when(userRepository).findById(any());
        doReturn(Optional.of(job)).when(jobRepository).findById(jobId);

        //when
        jobService.delete(jobId, userId);
        //then
    }

    @Test
    public void 유저id로지원전체조회성공() {
        //given
        List<Job> jobs = new ArrayList<>();
        Job job = Job.builder().build();
        Job job1 = Job.builder().build();
        jobs.add(job);
        jobs.add(job1);
        doReturn(Optional.of(user(1L))).when(userRepository).findById(any());
        doReturn(jobs).when(jobRepository).findAllByUserOrderByApplyDateDesc(any());
        //when
        List<JobResponse> result = jobService.findByUser(1L);
        //then
        assertThat(result.size()).isEqualTo(2);
    }

    @Nested
    @DisplayName("지원 내역 조회")
    class FindAll {

        @Test
        void 조건에맞는조회실패_throw발생() {
            //given
            doThrow(JobException.class).when(jobRepositoryCustom).findByCond(any(),any());
            //when
            assertThatThrownBy(() -> jobService.findByCond(any(),any())).isInstanceOf(JobException.class);
            //then
        }

        @Test
        void 조건에맞는조회성공() {
            //given
            User user = user(1L);
            doReturn(Optional.of(user)).when(userRepository).findById(user.getId());
            doReturn(Arrays.asList(Job.builder().build(), Job.builder().build())).when(jobRepositoryCustom).findByCond(any(), any());
            JobSearchCondition cond = new JobSearchCondition();
            //when
            List<JobResponse> result = jobService.findByCond(user.getId(), cond);
            //then
            assertThat(result.size()).isEqualTo(2);
        }
    }


    public JobRequest jobRequest() {
        return JobRequest.builder()
                .companyName("test")
                .huntingSite("test site")
                .employeesNumber(100)
                .location("서울특별시")
                .position("백엔드")
                .requiredCareer("신입")
                .primarySkill(Arrays.asList(Skill.builder().build()))
                .applyDate(LocalDate.now())
                .result("합격")
                .note("좋은 회사")
                .build();
    }

    private Job job(Long jobId) {
        return Job.builder()
                .id(jobId)
                .companyName("test")
                .huntingSite("test site")
                .employeesNumber(100)
                .location("서울특별시")
                .position("백엔드")
                .requiredCareer("신입")
//                .primarySkill(Arrays.asList(Skill.builder().build()))
                .applyDate(LocalDate.now())
                .result("합격")
                .note("좋은 회사")
                .user(user(1L))
                .build();
    }

    public User user(Long userId) {
        return User.builder()
                .id(userId)
                .uid("nimoh")
                .name("nimoh")
                .password("12345678")
                .email("test@test.com")
                .build();
    }
}
