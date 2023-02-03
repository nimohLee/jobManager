package com.nimoh.jobManager.repository;

import com.nimoh.jobManager.data.entity.Job;
import com.nimoh.jobManager.data.entity.Skill;
import com.nimoh.jobManager.data.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

//DataJpaTest에 Transactional 어노테이션이 DB 사용 후 자동 롤백해줌
//@DataJpaTest
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@SpringBootTest
@Transactional
public class JobRepositoryTest {

    @Autowired
    JobRepository jobRepository;

    @Autowired
    UserRepository userRepository;

    private User user;

//    @BeforeEach
//    public void init(){
//        // 연관관계를 위해 유저 미리 하나 생성
//        user = saveUser(1L);
//    }

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
        final Job job = job(user);
        //when
        Job result = jobRepository.save(job);
        //then
        assertThat(result).isNotNull();
    }

    @Test
    public void 지원모두조회() {
        //given
         final Job job = job(user);
         final Job job2 = job(user);

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
        final Job job = job(user);
        Job savedJob = jobRepository.save(job);
        //when
        final Optional<Job> findJobResult = jobRepository.findById(savedJob.getId());

        //then
        assertThat(findJobResult.isPresent()).isTrue();
    }
    @Test
    public void 유저아이디로지원내역모두조회() {
        //given
        user = saveUser(1L);
        final Job job = job(user);
        final Job job2 = job(user);

        jobRepository.save(job);
        jobRepository.save(job2);

        //when
        final List<Job> findJobResult = jobRepository.findAllByUser(User.builder().id(user.getId()).build());

        //then
        assertThat(findJobResult.size()).isEqualTo(2);
    }

    @Test
    public void 회사명으로지원조회() {
        //given
        final Job job = job( user);
        final Job job2 = job( user);

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
        final Job job = job(user);

        //when
        Job savedJob = jobRepository.save(job);
        Job updatedJob = jobRepository.save(Job.builder().id(savedJob.getId()).companyName("updated").build());
        //then
        Optional<Job> resultJob = jobRepository.findById(savedJob.getId());
        assertThat(resultJob.get().getCompanyName()).isEqualTo("updated");
    }

    @Test
    public void 지원삭제하기() {
        //given
        final Job job =job(user);
        Job savedJob = jobRepository.save(job);
        //when
        jobRepository.deleteById(savedJob.getId());
        //then
        Optional<Job> resultJob = jobRepository.findById(1L);
        assertThat(resultJob).isEmpty();
    }

    private Job job(User user){
        return Job.builder()
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