package com.megamaker.loginservice.security.oauth2;

import java.util.Map;

public class NaverResponse implements OAuth2Response {

    private final Map<String, Object> attributeMap;

    public NaverResponse(Map<String, Object> attributeMap) {
        this.attributeMap = (Map<String, Object>) attributeMap.get("response");
    }

    @Override
    public String getProvider() {
        return "static/naver";
    }

    @Override
    public String getProviderId() {
        return attributeMap.get("id").toString();
    }

    @Override
    public String getEmail() {
        return attributeMap.get("email").toString();
    }

    @Override
    public String getName() {
        return attributeMap.get("name").toString();
    }
}
