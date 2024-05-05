package com.megamaker.loginservice.legacy.repository;

import com.megamaker.loginservice.legacy.entity.OAuth2AccessToken;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.time.Duration;

@RequiredArgsConstructor
@Repository
public class TokenRepositoryRedis implements TokenRepository {
    private final RedisTemplate<String, String> redisTemplate;

    @Override
    public void save(OAuth2AccessToken oAuth2AccessToken) {
        redisTemplate.opsForValue().set(oAuth2AccessToken.getUserId(), oAuth2AccessToken.getToken(),
                Duration.ofMillis(oAuth2AccessToken.getTimeToLive()));
    }

    @Override
    public String get(String userId) {
        return redisTemplate.opsForValue().get(userId);
    }

    @Override
    public Boolean delete(String userId) {
        return redisTemplate.delete(userId);
    }
}
