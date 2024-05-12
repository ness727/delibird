package com.megamaker.loginservice.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.IOException;

@Slf4j
@Controller
@RequiredArgsConstructor
public class LogoutController {
    private final Environment environment;

    @PostMapping("/logout")
    public void logout(@CookieValue(value = "Auth") String userToken, HttpServletResponse response) throws IOException {
        // 토큰 쿠키 삭제
        Cookie cookie = new Cookie("Auth", null);

        cookie.setMaxAge(0);
        response.addCookie(cookie);
        response.sendRedirect(environment.getProperty("client.address"));


    }
}
