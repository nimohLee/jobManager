package com.nimoh.hotel.service;

import com.nimoh.hotel.domain.User;
import com.nimoh.hotel.dto.user.UserSignUpRequest;
import com.nimoh.hotel.errors.RoomException;
import com.nimoh.hotel.repository.UserRepository;
import com.nimoh.hotel.service.user.UserService;
import com.nimoh.hotel.service.user.UserServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.internal.bytebuddy.matcher.ElementMatchers.any;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
        doReturn(User.builder().build()).when(userRepository).findByUid(any());
        UserSignUpRequest request = UserSignUpRequest.builder()
                .uid("nimoh")
                .name("니모")
                .password("12345678910a")
                .email("nimoh@email.com")
                .build();
        //when
        final RoomException result = assertThrows(UserException.class, () -> userService.signUp(request));
        //then

        assertThat(result.getErrorResult()).isEqualTo(UserErrorResult.DUPLICATE_USER_ID);
    }
}
