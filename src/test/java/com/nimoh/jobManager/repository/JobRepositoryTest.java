package com.nimoh.jobManager.repository;

import com.nimoh.jobManager.data.entity.Job;
import com.nimoh.jobManager.data.entity.Skill;
import com.nimoh.jobManager.data.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

//DataJpaTest에 Transactional 어노테이션이 DB 사용 후 자동 롤백해줌
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class JobRepositoryTest {

    @Autowired
    JobRepository jobRepository;

    @Autowired
    UserRepository userRepository;

    private User user;

    @BeforeEach
    public void init(){
        // 연관관계를 위해 유저 미리 하나 생성
        user = saveUser(1L);
    }

    private User saveUser(Long userId){
        User user = User.builder()
                .id(userId)
                .uid("nimoh123")
                .name("nimoh")
                .email("test@test.com")
                .password("12345678")
                .build();
        userRepository.save(user);
        return user;
    }

    @Test
    public void 지원내역작성하기() {
        //given
        final Job job = job(2L,user);
        //when
        Job result = jobRepository.save(job);
        //then
        assertThat(result).isNotNull();
    }

    @Test
    public void 지원모두조회() {
        //given
         final Job job = job(2L,user);
         final Job job2 = job(3L,user);

        jobRepository.save(job);
        jobRepository.save(job2);
        //when
        final List<Job> findJobResult = jobRepository.findAll();

        //then
        assertThat(findJobResult.size()).isEqualTo(2);
    }

    @Test
    public void 지원하나조회성공() {
        //given
        final Job job = job(1L,user);
        jobRepository.save(job);
        //when
        final Optional<Job> findJobResult = jobRepository.findById(1L);

        //then
        assertThat(findJobResult.get().getId()).isEqualTo(1L);
    }
    @Test
    public void 유저아이디로지원내역모두조회() {
        //given
        final Job job = job(2L,user);
        final Job job2 = job(3L, user);

        jobRepository.save(job);
        jobRepository.save(job2);

        //when
        final List<Job> findJobResult = jobRepository.findAllByUser(User.builder().id(1L).build());

        //then
        assertThat(findJobResult.size()).isEqualTo(2);
    }

    @Test
    public void 회사명으로지원조회() {
        //given
        final Job job = job(2L, user);

        final Job job2 = job(3L, user);

        jobRepository.save(job);
        jobRepository.save(job2);

        //when
        final List<Job> findJobResult = jobRepository.findAllByCompanyNameContaining("nimoh");

        //then
        assertThat(findJobResult.size()).isEqualTo(2);
    }

    @Test
    public void 지원정보수정하기() {
        //given
        final Job job = job(1L, user);
        final Job updateJob = Job.builder().id(1L).companyName("updated").build();

        //when
        jobRepository.save(job);
        jobRepository.save(updateJob);
        //then
        Optional<Job> resultJob = jobRepository.findById(1L);
        assertThat(resultJob.get().getCompanyName()).isEqualTo("updated");
    }

    @Test
    public void 지원삭제하기() {
        //given
        final Job job =job(1L, user);
        jobRepository.save(job);
        //when
        jobRepository.deleteById(1L);
        //then
        Optional<Job> resultJob = jobRepository.findById(1L);
        assertThat(resultJob).isEmpty();
    }

    private Job job(Long jobId, User user){
        return Job.builder()
                .id(jobId)
                .companyName("nimoh company")
                .employeesNumber(25)
                .huntingSite("사람인")
                .location("행복시 행복동")
                .applyDate(LocalDate.now())
                .position("백엔드")
                .requiredCareer("신입")
                .primarySkill(Arrays.asList(Skill.builder().name("Java").build()))
                .result("합격")
                .note("모르겠음")
                .user(user)
                .build();
    }
}
