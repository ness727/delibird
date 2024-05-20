package com.megamaker.storeservice.security;

import com.megamaker.storeservice.dto.user.ResponseUser;
import com.megamaker.storeservice.feignclient.UserClient;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class LoginFilter extends OncePerRequestFilter {
    private final UserClient userClient;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        // 쿠키에서 jwt 가져옴
        String jwt = null;
        for (Cookie cookie : request.getCookies()) {
            if (cookie.getName().equals("Auth")) {
                jwt = cookie.getValue();
            }
        }

        try {
            ResponseUser foundUser = userClient.getUserByToken(jwt);  // 유저 서비스에서 유저 정보 조회
            // 새 유저 인증 객체 생성
            Authentication auth = UserAuthentication.builder()
                    .principal(foundUser.getUserId())
                    .regionCode(foundUser.getRegionCode())
                    .authorities(List.of(new SimpleGrantedAuthority(foundUser.getStatus())))
                    .build();

            SecurityContextHolder.getContext().setAuthentication(auth);
        } catch (RuntimeException e) {
            log.debug("인증 정보 불일치");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }
        doFilter(request, response, filterChain);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        // 매장 검색 또는 카테고리 선택 시
        return !request.getServletPath().equals("/stores") && request.getMethod().equals("GET");
    }
}
