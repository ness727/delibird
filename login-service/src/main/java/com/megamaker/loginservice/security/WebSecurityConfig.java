package com.megamaker.loginservice.security;

import com.megamaker.loginservice.security.filter.AfterLoginFilter;
import com.megamaker.loginservice.security.oauth2.CustomOAuth2UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.FormLoginConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.client.web.OAuth2LoginAuthenticationFilter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AbstractAuthenticationTargetUrlRequestHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.web.cors.CorsConfiguration;

import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

@RequiredArgsConstructor
@EnableWebSecurity
@Configuration
public class WebSecurityConfig {
    private final CustomOAuth2UserService customOAuth2UserService;
    private final CustomCorsConfig customCorsConfig;
    private final LoginSuccessHandler loginSuccessHandler;

    @Bean
    public WebSecurityCustomizer configure() {
        return web -> web.ignoring()
                .requestMatchers("/img/**", "/css/**", "/js/**", "/assets/**", "/error");  // 정적 자원은 필터 무시
    }

    // 특정 Http 요청에 대한 보안 설정
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                // CSRF 테스트를 위한 비활성화
                .csrf(AbstractHttpConfigurer::disable)

                // CORS 허용 커스텀 설정
//                .cors(c -> c
//                        .configurationSource(customCorsConfig)
//                )
                .cors(AbstractHttpConfigurer::disable)
                //.addFilterBefore(afterLoginFilter(), OAuth2LoginAuthenticationFilter.class)

                // httpBasic 비활성화
                .httpBasic(AbstractHttpConfigurer::disable)

                // formLogin 비활성화
                .formLogin(FormLoginConfigurer::disable)

                // 세션 비활성화
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )

                // OAuth2 클라이언트 설정
                .oauth2Login(authConfig -> authConfig
                        .userInfoEndpoint(endpointConfig -> endpointConfig
                                .userService(customOAuth2UserService)
                        )
                        .loginPage("/login")  // 커스텀 로그인 페이지 설정
                        //.defaultSuccessUrl("/", true)
                        .successHandler(loginSuccessHandler)
                )

                // 어느 경로를 인증받지 않고 사용할 수 있는지 설정
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(antMatcher("/login/**"), antMatcher("/oauth2/**"), antMatcher("/signup")
                                , antMatcher("/logout"))
                        .permitAll()
                        .anyRequest()
                        .authenticated()
                )

                .build();
    }

    @Bean
    public AfterLoginFilter afterLoginFilter() {
        return new AfterLoginFilter();
    }
}
