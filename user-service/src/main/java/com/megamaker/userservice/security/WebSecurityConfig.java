package com.megamaker.userservice.security;

import com.megamaker.userservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.FormLoginConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

@RequiredArgsConstructor
@EnableWebSecurity
@Configuration
public class WebSecurityConfig {
    private final Environment environment;
    private final UserRepository userRepository;

    @Bean
    public WebSecurityCustomizer configure() {
        return web -> web.ignoring()
                .requestMatchers("/img/**", "/css/**", "/js/**", "/assets/**",
                        "/error", "/actuator/**");  // 정적 자원은 필터 무시
    }

    // 특정 Http 요청에 대한 보안 설정
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)

                .cors(AbstractHttpConfigurer::disable)

                // httpBasic 비활성화
                .httpBasic(AbstractHttpConfigurer::disable)

                // formLogin 비활성화
                .formLogin(FormLoginConfigurer::disable)

                // httpBasic 비활성화
                .httpBasic(AbstractHttpConfigurer::disable)

                // JWT로 userId 얻어 저장하는 필터 추가
                .addFilterAfter(jwtFilter(), UsernamePasswordAuthenticationFilter.class)

                // 세션 비활성화
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )

                // 자체 로그아웃 비활성화
                .logout(AbstractHttpConfigurer::disable)

                // 어느 경로를 인증받지 않고 사용할 수 있는지 설정
                .authorizeHttpRequests(auth -> auth
                        .anyRequest()
                        .permitAll()
                )

                .build();
    }

    @Bean
    public JwtFilter jwtFilter() {
        return new JwtFilter(environment, userRepository);
    }
}
