package com.megamaker.loginservice.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.IOException;

@Controller
public class LogoutController {
    @PostMapping("/logout")
    public void logout(HttpServletResponse response) throws IOException {
        Cookie cookie = new Cookie("Auth", null);

        cookie.setMaxAge(0);
        response.addCookie(cookie);
        response.sendRedirect("http://localhost:8000/");
    }
}
