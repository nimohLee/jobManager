package com.nimoh.hotel.service.user;

import com.nimoh.hotel.data.dto.user.UserLogInRequest;
import com.nimoh.hotel.data.dto.user.UserResponse;
import com.nimoh.hotel.data.dto.user.UserSignUpRequest;

public interface UserService {
    public UserResponse signUp(UserSignUpRequest request);
    public boolean deleteById(Long userId);

    public UserResponse login(UserLogInRequest request);
}
