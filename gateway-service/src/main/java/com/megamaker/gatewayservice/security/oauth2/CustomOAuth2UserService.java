package com.megamaker.gatewayservice.security.oauth2;

import com.megamaker.userservice.Repository.UserRepository;
import com.megamaker.userservice.domain.User;
import com.megamaker.userservice.dto.GoogleResponse;
import com.megamaker.userservice.dto.KakaoResponse;
import com.megamaker.userservice.dto.NaverResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {
    private final UserRepository userRepository;

    @Transactional
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        OAuth2Response response = switch (registrationId) {
            case "naver" -> new NaverResponse(oAuth2User.getAttributes());
            case "kakao" -> new KakaoResponse(oAuth2User.getAttributes()); // 카카오는 권한이 없어서 이름 대신 닉네임으로 설정
            case "google" -> new GoogleResponse(oAuth2User.getAttributes());
            default -> null;
        };

        if (response == null) throw new OAuth2AuthenticationException("로그인 실패");
        Optional<User> foundUser = userRepository.findByProviderId(response.getProviderId());
        User user;

        // 신규 회원일 때
        if (foundUser.isEmpty()) {
            String oAuth2UserId = UUID.randomUUID().toString() + "@" + response.getProvider().charAt(0);
            user = User.builder()
                    .userId(oAuth2UserId)
                    .providerId(response.getProviderId())
                    // .nickname()
                    // .phone()
                    // .address()
                    .status(User.Status.USER)
                    .build();
            userRepository.save(user);
        } 
        // 기존 회원일 때
        else {
            user = foundUser.get();
            // user.setUserName(response.getName());
        }

        return new CustomOAuth2User(response, user);
    }
}
