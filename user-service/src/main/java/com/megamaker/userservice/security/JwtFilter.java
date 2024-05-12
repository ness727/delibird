package com.megamaker.userservice.security;

import com.megamaker.userservice.domain.User;
import com.megamaker.userservice.repository.UserRepository;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.client.RestClientException;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
    private final Environment environment;
    private final UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        byte[] secretKeyBytes = Base64.getEncoder().encode(environment.getProperty("token.secret").getBytes());
        SecretKey secretKey = new SecretKeySpec(secretKeyBytes, Jwts.SIG.HS256.key().build().getAlgorithm());

        try {
            // 쿠키에서 jwt 가져옴
            String jwt = null;
            for (Cookie cookie : request.getCookies()) {
                if (cookie.getName().equals("Auth")) {
                    jwt = cookie.getValue();
                }
            }

            JwtParser jwtParser = Jwts.parser()
                    .verifyWith(secretKey)
                    .build();
            String userId = jwtParser.parseSignedClaims(jwt).getPayload().getSubject();

            // 회원 조회
            User foundUser = userRepository.findByUserId(userId).orElseThrow();

            Authentication auth = new UsernamePasswordAuthenticationToken(  // 새 유저 인증 객체 생성
                    foundUser.getUserId(),
                    null,
                    List.of(new SimpleGrantedAuthority(foundUser.getStatus().name()))
            );

            SecurityContextHolder.getContext().setAuthentication(auth);
            doFilter(request, response, filterChain);
        } catch (RestClientException e) {
            log.debug("인증 정보 불일치");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return (request.getServletPath().equals("/users") && request.getMethod().equals("POST"))
                || request.getServletPath().equals("/users/check");
    }
}
