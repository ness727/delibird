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
public class JwtFilter extends OncePerRequestFilter {
    private final UserClient userClient;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String jwt = null;

        // 헤더에서 JWT 가져옴
        // 주로 다른 서비스에서 feign 요청을 보냈을 때
        String headerToken = request.getHeader("Auth");
        if (headerToken != null) {
            jwt = headerToken;
        }

        // 쿠키에서 JWT 가져옴
        // 주로 게이트웨이 -> 바로 이 서비스로 요청 전달 시
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if (cookie.getName().equals("Auth")) {
                    jwt = cookie.getValue();
                }
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
            return;
        }
        doFilter(request, response, filterChain);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        // 매장 검색 또는 카테고리 선택 시
        return (!request.getServletPath().equals("/stores") && request.getMethod().equals("GET"))
                || request.getServletPath().equals("/products");
    }
}
