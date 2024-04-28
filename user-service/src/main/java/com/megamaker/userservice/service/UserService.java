package com.megamaker.userservice.service;

import com.megamaker.userservice.Repository.UserRepository;
import com.megamaker.userservice.domain.User;
import com.megamaker.userservice.dto.RequestRegisterUser;
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
        User userEntity = UserMapper.INSTANCE.requestRegisterUserToUser(user);
        User savedUser = userRepository.save(userEntity);
        return UserMapper.INSTANCE.requestRegisterUserToResponseUser(user);
    }

    public ResponseUser getUser(String userId) {
        User foundUser = userRepository.findByUserId(userId).orElseThrow();
        return UserMapper.INSTANCE.userToResponseUser(foundUser);
    }

    public boolean isUserAlreadyRegistered(String providerId) {
        return userRepository.findByProviderId(providerId).isPresent();
    }
}
