package com.nimoh.jobManager.repository;

import com.nimoh.jobManager.data.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

//@DataJpaTest
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Test
    public void 회원가입성공() {
        //given
        User user = user();
        userRepository.save(user);
        //when
        List<User> users = userRepository.findAll();
        //then
        assertThat(users.size()).isEqualTo(1);
    }

    @Test
    public void 회원탈퇴성공() {
        //given
        User user = user(); // id = 1L
        User savedUser = userRepository.save(user);
        //when
        userRepository.deleteById(savedUser.getId());
        Optional<User> result = userRepository.findById(1L);
        //then
        assertThat(result).isEmpty();
    }

    private User user(){
        return User.builder()
                .id(1L)
                .uid("nimoh")
                .name("니모")
                .email("email@email.com")
                .password("password")
                .build();

    }
}
