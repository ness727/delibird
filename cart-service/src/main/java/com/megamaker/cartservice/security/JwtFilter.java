package com.megamaker.cartservice.security;

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

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String jwt = null;

        // 헤더에서 JWT 가져옴 보냈을 때
        String headerToken = request.getHeader("Auth");
        if (headerToken != null) {
            jwt = headerToken;
        }
        else {  // 쿠키에서 JWT 가져옴
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

            Authentication auth = new UsernamePasswordAuthenticationToken(  // 새 유저 인증 객체 생성
                    userId,
                    null,
                    null
            );

            SecurityContextHolder.getContext().setAuthentication(auth);
            doFilter(request, response, filterChain);
        } catch (RuntimeException e) {
            log.debug("인증 정보 불일치");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return super.shouldNotFilter(request);
    }
}
