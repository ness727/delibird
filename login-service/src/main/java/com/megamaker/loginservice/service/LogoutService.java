package com.megamaker.loginservice.service;

import com.megamaker.loginservice.legacy.repository.TokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LogoutService {
    private final TokenRepository tokenRepository;

    public void deleteAccessTokenFromRedis(String userId) {
        //tokenRepository.delete()
    }
}
