package com.nimoh.jobManager.repository.job;

import com.nimoh.jobManager.commons.enums.Result;
import com.nimoh.jobManager.data.dto.job.JobSearchCondition;
import com.nimoh.jobManager.data.entity.Job;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class JobRepositoryCustomImplTest {

    @Autowired
    JobRepositoryCustomImpl jobRepositoryCustom;
    @Autowired
    JobRepository jobRepository;

    @Test
    void findByCond() {
        //given
        jobRepository.save(Job.builder().id(1L).companyName("합격한 좋은회사").result(Result.APPLICATION_COMPLETE.getResultName()).build());
        jobRepository.save(Job.builder().id(2L).companyName("탈락한 좋은회사").result(Result.FAIL.getResultName()).build());
        JobSearchCondition cond = new JobSearchCondition();
        cond.setName("은회");
        cond.setResult(Result.APPLICATION_COMPLETE);
        //when
        List<Job> byCond = jobRepositoryCustom.findByCond(cond);
        //then
        assertThat(byCond.get(0).getId()).isEqualTo(1L);
    }
}