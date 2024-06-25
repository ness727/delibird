package com.megamaker.userservice.security;

import com.megamaker.userservice.entity.User;
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
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.util.Base64;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
    private final Environment environment;
    private final UserRepository userRepository;

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
            byte[] secretKeyBytes = Base64.getEncoder().encode(environment.getProperty("token.secret").getBytes());
            SecretKey secretKey = new SecretKeySpec(secretKeyBytes, Jwts.SIG.HS256.key().build().getAlgorithm());

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
        } catch (RuntimeException e) {
            log.debug("인증 정보 불일치", e);
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return !request.getServletPath().equals("/users");
    }
}
