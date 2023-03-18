package com.nimoh.jobManager.service;

import com.nimoh.jobManager.config.jwt.JwtTokenProvider;
import com.nimoh.jobManager.data.dto.user.UserLogInRequest;
import com.nimoh.jobManager.data.entity.User;
import com.nimoh.jobManager.data.dto.user.UserResponse;
import com.nimoh.jobManager.data.dto.user.UserSignUpRequest;
import com.nimoh.jobManager.exception.user.UserErrorResult;
import com.nimoh.jobManager.exception.user.UserException;
import com.nimoh.jobManager.repository.UserRepository;
import com.nimoh.jobManager.service.user.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    UserRepository userRepository;

    @Mock
    PasswordEncoder passwordEncoder;

    @Mock
    JwtTokenProvider jwtTokenProvider;

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
    public void 회원탈퇴실패_해당유저없음() {
        //given
        when(userRepository.findById(any())).thenReturn(Optional.empty()).thenThrow(new UserException(UserErrorResult.USER_NOT_FOUND));
        //when
        UserException result = assertThrows(UserException.class, () -> userService.deleteById(0L));
        //then
        assertThat(result.getErrorResult()).isEqualTo(UserErrorResult.USER_NOT_FOUND);
    }

    @Test
    public void 회원탈퇴성공() {
        //given
        when(userRepository.findById(any())).thenReturn(Optional.of(user()));
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
        doReturn(false).when(passwordEncoder).matches(any(), any());
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
        doReturn(true).when(passwordEncoder).matches(any(), any());
        doReturn("123.442.13").when(jwtTokenProvider).createToken(any(), any());
        //when
        String result = userService.login(request).get("accessToken");
        //then
        assertThat(result).isNotEmpty();
    }

    private User user() {
        return User.builder()
                .id(1L)
                .uid("nimoh123")
                .name("니모")
                .email("email@email.com")
                .password("password123")
                .build();
    }
}
