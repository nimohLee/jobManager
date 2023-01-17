package com.nimoh.hotel.service.user;

import com.nimoh.hotel.data.dto.user.UserLogInRequest;
import com.nimoh.hotel.data.entity.User;
import com.nimoh.hotel.data.dto.user.UserResponse;
import com.nimoh.hotel.data.dto.user.UserSignUpRequest;
import com.nimoh.hotel.commons.user.UserErrorResult;
import com.nimoh.hotel.commons.user.UserException;
import com.nimoh.hotel.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserResponse signUp(UserSignUpRequest request) {
        Optional<User> duplicateResult = userRepository.findByUid(request.getUid());
        if (duplicateResult.isPresent()){
            throw new UserException(UserErrorResult.DUPLICATED_USER_ID);
        }

        String encodedPw = passwordEncoder.encode(request.getPassword());
        User signUpUser = User.builder()
                .uid(request.getUid())
                .name(request.getName())
                .email(request.getEmail())
                .password(encodedPw)
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

    @Override
    public UserResponse login(UserLogInRequest request) {
        Optional<User> findUser = userRepository.findByUid(request.getUid());
        if (findUser.isEmpty()) {
            throw new UserException(UserErrorResult.USER_NOT_FOUND);
        }
        String encodedPw = findUser.get().getPassword();

        if(passwordEncoder.matches(request.getPassword(), encodedPw)){
            return UserResponse.builder()
                    .id(findUser.get().getId())
                    .uid(findUser.get().getUid())
                    .email(findUser.get().getEmail())
                    .name(findUser.get().getName())
                    .build();
        }else{
            throw new UserException(UserErrorResult.WRONG_PASSWORD);
        }
    }
}
