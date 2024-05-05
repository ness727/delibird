package com.megamaker.loginservice.legacy.repository;

import com.megamaker.loginservice.legacy.entity.OAuth2AccessToken;

public interface TokenRepository {
    void save(OAuth2AccessToken oAuth2AccessToken);
    String get(String userId);
    Boolean delete(String userId);
}
