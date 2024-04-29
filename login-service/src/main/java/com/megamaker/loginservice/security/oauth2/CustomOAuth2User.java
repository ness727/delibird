package com.megamaker.loginservice.security.oauth2;

import com.megamaker.loginservice.dto.RequestRegisterUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

@RequiredArgsConstructor
public class CustomOAuth2User implements OAuth2User {
    private final OAuth2Response oAuth2Response;
    private final RequestRegisterUser user;

    @Override
    public Map<String, Object> getAttributes() {
        return null;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Set.of(new SimpleGrantedAuthority(user.getStatus().name()));
    }

    @Override
    public String getName() {
        return oAuth2Response.getName();
    }

    public String getUserId() {
        return user.getUserId();
    }

    public String getProviderId() {
        return oAuth2Response.getProviderId();
    }
}
