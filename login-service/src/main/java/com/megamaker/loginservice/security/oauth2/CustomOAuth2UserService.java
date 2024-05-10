package com.megamaker.loginservice.security.oauth2;


import com.megamaker.loginservice.dto.RequestCheckUser;
import com.megamaker.loginservice.dto.RequestRegisterUser;
import com.megamaker.loginservice.dto.ResponseCheckUser;
import com.megamaker.loginservice.vo.Provider;
import com.megamaker.loginservice.vo.UserStatus;
import com.megamaker.loginservice.feignclient.UserClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {
    private final Environment environment;
    private final UserClient userClient;

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

        RequestRegisterUser user = null;
        ResponseCheckUser result = userClient.isUserAlreadyRegistered(new RequestCheckUser(response.getProviderId()));

        if (result.getUserId() != null) {  // 기존 회원일 때
            user = RequestRegisterUser.builder()
                    .userId(result.getUserId())
                    .providerId(result.getProviderId())
                    .status(result.getStatus())
                    .build();
        } else {  // 신규 회원일 때
            String oAuth2UserId = UUID.randomUUID().toString();
            Provider provider = Provider.NONE;

            if (response.getProvider().contains("naver")) {
                provider = Provider.NAVER;
            } else if (response.getProvider().contains("kakao")) {
                provider = Provider.KAKAO;
            } else if (response.getProvider().contains("google")) {
                provider = Provider.GOOGLE;
            }

            user = RequestRegisterUser.builder()
                    .userId(oAuth2UserId)
                    .provider(provider)
                    .providerId(response.getProviderId())
                    .status(UserStatus.USER)
                    .accessToken(userRequest.getAccessToken().getTokenValue())
                    .build();
            userClient.register(user);
        }

        return new CustomOAuth2User(response, user);
    }
}
