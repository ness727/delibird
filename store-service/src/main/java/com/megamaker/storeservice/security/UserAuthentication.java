package com.megamaker.storeservice.security;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;


public class UserAuthentication extends UsernamePasswordAuthenticationToken {
    private String regionCode;

    @Builder
    public UserAuthentication(Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities,
                              String regionCode) {
        super(principal, credentials, authorities);
        this.regionCode = regionCode;
    }

    public String getRegionCode() {
        return this.regionCode;
    }

}
