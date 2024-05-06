package com.megamaker.userservice.service;

import com.megamaker.userservice.Repository.UserRepository;
import com.megamaker.userservice.domain.User;
import com.megamaker.userservice.dto.RequestRegisterUser;
import com.megamaker.userservice.dto.ResponseCheckUser;
import com.megamaker.userservice.dto.ResponseRegisterUser;
import com.megamaker.userservice.dto.ResponseUser;
import com.megamaker.userservice.util.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public ResponseRegisterUser register(RequestRegisterUser user) {
        User userEntity = UserMapper.INSTANCE.toUser(user);
        User result = userRepository.save(userEntity);
        return UserMapper.INSTANCE.toResponseRegisterUser(result);
    }

    public ResponseUser getUser(String userId) {
        User foundUser = userRepository.findByUserId(userId).orElseThrow();
        return UserMapper.INSTANCE.toResponseUser(foundUser);
    }

    public ResponseCheckUser isUserAlreadyRegistered(String providerId) {
        User foundUser = userRepository.findByProviderId(providerId).orElse(new User());
        return UserMapper.INSTANCE.toResponseCheckUser(foundUser);
    }
}
