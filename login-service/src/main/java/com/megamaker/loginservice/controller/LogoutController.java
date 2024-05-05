package com.megamaker.loginservice.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.IOException;

@Slf4j
@Controller
@RequiredArgsConstructor
public class LogoutController {

    @PostMapping("/logout")
    public void logout(@CookieValue(value = "Auth") String userToken, HttpServletResponse response) throws IOException {
        // 토큰 쿠키 삭제
        Cookie cookie = new Cookie("Auth", null);

        cookie.setMaxAge(0);
        response.addCookie(cookie);
        response.sendRedirect("http://localhost:8000/");


    }
}
