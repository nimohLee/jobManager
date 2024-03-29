package com.nimoh.jobManager.repository.job;

import com.nimoh.jobManager.commons.enums.JobSearchSite;
import com.nimoh.jobManager.commons.enums.RequiredExperience;
import com.nimoh.jobManager.commons.enums.Result;
import com.nimoh.jobManager.data.dto.job.JobSearchCondition;
import com.nimoh.jobManager.data.entity.Job;
import com.nimoh.jobManager.data.entity.User;
import com.nimoh.jobManager.repository.UserRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
public class JobRepositoryCustomImplTest {

    @Autowired
    JobRepositoryCustom jobRepositoryCustom;

    @Autowired
    JobRepository jobRepository;

    User user;
    @Autowired
    private UserRepository userRepository;

    @TestConfiguration
    static class TestConfig{
        @PersistenceContext
        private EntityManager em;

        @Bean
        public JobRepositoryCustom jobRepositoryCustom() {
            return new JobRepositoryCustomImpl(jpaQueryFactory());
        }

        @Bean
        public JPAQueryFactory jpaQueryFactory() {
            return new JPAQueryFactory(em);
        }
    }

    @BeforeEach
    void init() {
        user = User.builder().uid("test").email("email").password("password").name("test").build();
        userRepository.save(user);
    }

    @AfterEach
    void after() {
        jobRepository.deleteAll();
    }

    @Test
    void 이름으로검색성공() {
        //given
        jobRepository.save(Job.builder().user(user).companyName("합격한 좋은회사").build()); // 1L
        jobRepository.save(Job.builder().user(user).companyName("불합격한 아무회사").build()); // 2L
        JobSearchCondition cond = new JobSearchCondition();
        cond.setName("좋은");
        //when
        List<Job> byCond = jobRepositoryCustom.findByCond(user.getId(),cond);
        //then
        assertThat(byCond.size()).isEqualTo(1);
    }

    @Test
    void 결과로검색성공() {
        //given
        jobRepository.save(Job.builder().user(user).result(Result.PASS.getResultName()).build());
        jobRepository.save(Job.builder().user(user).result(Result.FAIL.getResultName()).build());
        JobSearchCondition cond = new JobSearchCondition();
        cond.setResult(Result.PASS);
        //when
        List<Job> byCond = jobRepositoryCustom.findByCond(user.getId(),cond);
        //then
        assertThat(byCond.size()).isEqualTo(1);
    }

    @Test
    void 요구경력으로검색성공() {
        //given
        jobRepository.save(Job.builder().user(user).requiredCareer(RequiredExperience.NEW.getCareer()).build());
        jobRepository.save(Job.builder().user(user).requiredCareer(RequiredExperience.EXPERIENCED.getCareer()).build());
        JobSearchCondition cond = new JobSearchCondition();
        cond.setRequiredExperience(RequiredExperience.NEW);
        //when
        List<Job> byCond = jobRepositoryCustom.findByCond(user.getId(),cond);
        //then
        assertThat(byCond.size()).isEqualTo(1);
    }

    @Test
    void 연봉으로검색성공() {
        //given
        jobRepository.save(Job.builder().user(user).salary(3700).build());
        jobRepository.save(Job.builder().user(user).salary(5000).build());
        JobSearchCondition cond = new JobSearchCondition();
        cond.setMinSalary(3500);
        cond.setMaxSalary(4000);
        //when
        List<Job> byCond = jobRepositoryCustom.findByCond(user.getId(),cond);
        //then
        assertThat(byCond.size()).isEqualTo(1);
    }

    @Test
    void 지역으로검색성공() {
        //given
        jobRepository.save(Job.builder().user(user).location("부산광역시 해운대구").build());
        jobRepository.save(Job.builder().user(user).location("서울특별시 강남구").build());
        JobSearchCondition cond = new JobSearchCondition();
        cond.setLocation("부산");
        //when
        List<Job> byCond = jobRepositoryCustom.findByCond(user.getId(),cond);
        //then
        assertThat(byCond.size()).isEqualTo(1);
    }

    @Test
    void 구직사이트으로검색성공() {
        //given
        jobRepository.save(Job.builder().user(user).huntingSite(JobSearchSite.SARAMIN.getName()).build());
        jobRepository.save(Job.builder().user(user).location(JobSearchSite.JOBKOREA.getName()).build());
        JobSearchCondition cond = new JobSearchCondition();
        cond.setJobSearchSite(JobSearchSite.SARAMIN);
        //when
        List<Job> byCond = jobRepositoryCustom.findByCond(user.getId(),cond);
        //then
        assertThat(byCond.size()).isEqualTo(1);
    }

}
