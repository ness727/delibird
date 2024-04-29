package com.megamaker.loginservice.controller;

import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class LoginController {
    @GetMapping("/login")
    public String login(OAuth2AuthenticationToken token) {  // 익명 사용자는 자동 주입 처리 안 되는 것 주의!
        return "login";
    }

    @GetMapping("/")
    @ResponseBody
    public String hi() {
        return "hihihi";
    }
}
