package com.nimoh.hotel.service;

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

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    UserRepository userRepository;
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
        doReturn(user).when(userRepository).save(ArgumentMatchers.any(User.class));

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

}
