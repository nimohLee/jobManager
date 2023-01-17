package com.nimoh.hotel.service;

import com.nimoh.hotel.data.dto.user.UserLogInRequest;
import com.nimoh.hotel.data.entity.User;
import com.nimoh.hotel.data.dto.user.UserResponse;
import com.nimoh.hotel.data.dto.user.UserSignUpRequest;
import com.nimoh.hotel.commons.user.UserErrorResult;
import com.nimoh.hotel.commons.user.UserException;
import com.nimoh.hotel.repository.UserRepository;
import com.nimoh.hotel.service.user.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    UserRepository userRepository;

    @Mock
    PasswordEncoder passwordEncoder;

    @InjectMocks
    UserServiceImpl userService;



    @Test
    public void 회원가입실패_아이디중복() {
        //given
        doReturn(Optional.of(User.builder().build())).when(userRepository).findByUid("name");
        UserSignUpRequest request = UserSignUpRequest.builder()
                .uid("name")
                .name("니모")
                .password("12345678910a")
                .email("nimoh@email.com")
                .build();
        //when
        final UserException result = assertThrows(UserException.class, () -> userService.signUp(request));
        //then

        assertThat(result.getErrorResult()).isEqualTo(UserErrorResult.DUPLICATED_USER_ID);
    }

    @Test
    public void 회원가입성공() {
        //given
        UserSignUpRequest request = UserSignUpRequest.builder()
                .uid("name")
                .name("니모")
                .password("12345678910a")
                .email("nimoh@email.com")
                .build();
        User user = User.builder()
                .id(1L)
                .uid("nimoh")
                .email("nimoh@email.com")
                .name("니모")
                .password("123456789a")
                .build();
        doReturn(user).when(userRepository).save(any(User.class));

        //when
        UserResponse result = userService.signUp(request);
        //then
        assertThat(result.getUid()).isEqualTo("nimoh");
    }

    @Test
    public void 회원탈퇴성공() {
        //given
        doNothing().when(userRepository).deleteById(1L);
        //when
        boolean result = userService.deleteById(1L);
        //then
        assertThat(result).isTrue();
    }

    @Test
    public void 로그인실패_해당아이디에해당하는유저없음() {
        //given
        UserLogInRequest request = UserLogInRequest.builder()
                .uid("nimoh123")
                .password("password123")
                .build();
        doReturn(Optional.empty()).when(userRepository).findByUid(any());
        //when
        final UserException result = assertThrows(UserException.class, () -> userService.login(request));
        //then
        assertThat(result.getErrorResult()).isEqualTo(UserErrorResult.USER_NOT_FOUND);
    }

    @Test
    public void 로그인실패_비밀번호틀림() {
        //given
        UserLogInRequest request = UserLogInRequest.builder()
                .uid("nimoh123")
                .password("password123")
                .build();
        doReturn(Optional.of(user())).when(userRepository).findByUid(any());
        doReturn(false).when(passwordEncoder).matches(any(),any());
        //when
        final UserException result = assertThrows(UserException.class, () -> userService.login(request));
        //then
        assertThat(result.getErrorResult()).isEqualTo(UserErrorResult.WRONG_PASSWORD);
    }

    @Test
    public void 로그인성공() {
        //given
        UserLogInRequest request = UserLogInRequest.builder()
                .uid("nimoh123")
                .password("password123")
                .build();
        doReturn(Optional.of(user())).when(userRepository).findByUid(any());
        doReturn(true).when(passwordEncoder).matches(any(),any());
        //when
        UserResponse result = userService.login(request);
        //then
        assertThat(result.getUid()).isEqualTo("nimoh123");
    }

    private User user(){
        return User.builder()
                .id(1L)
                .uid("nimoh123")
                .name("니모")
                .email("email@email.com")
                .password("password123")
                .build();

    }
}
