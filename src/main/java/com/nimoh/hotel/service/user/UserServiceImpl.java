package com.nimoh.hotel.service.user;

import com.nimoh.hotel.data.entity.User;
import com.nimoh.hotel.data.dto.user.UserResponse;
import com.nimoh.hotel.data.dto.user.UserSignUpRequest;
import com.nimoh.hotel.commons.user.UserErrorResult;
import com.nimoh.hotel.commons.user.UserException;
import com.nimoh.hotel.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public UserResponse signUp(UserSignUpRequest request) {
        Optional<User> duplicateResult = userRepository.findByUid(request.getUid());
        if (duplicateResult.isPresent()){
            throw new UserException(UserErrorResult.DUPLICATED_USER_ID);
        }
        User signUpUser = User.builder()
                .uid(request.getUid())
                .name(request.getName())
                .email(request.getEmail())
                .password(request.getPassword())
                .build();
        User result = userRepository.save(signUpUser);
        return UserResponse.builder()
                .uid(result.getUid())
                .name(result.getName())
                .email(result.getEmail())
                .build();
    }

    @Override
    public boolean deleteById(Long userId) {
        userRepository.deleteById(userId);
        return true;
    }
}
