package com.megamaker.gatewayservice.filter;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpCookie;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;

import java.net.URI;

@Slf4j
@Component
public class BeforeLoginFilter extends AbstractGatewayFilterFactory<BeforeLoginFilter.Config> {
    private final Environment environment;

    @Autowired
    public BeforeLoginFilter(Environment environment) {
        super(Config.class);
        this.environment = environment;
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            ServerHttpResponse response = exchange.getResponse();
            MultiValueMap<String, HttpCookie> cookies = request.getCookies();

            // Auth 쿠키를 가지고 있을 때 로그인 페이지 접속 막음
            if (cookies.containsKey("Auth")) {
                String referer = request.getHeaders().getFirst("Referer");
                URI uri;

                if (referer != null) {
                    uri = URI.create(referer);
                } else {
                    uri = URI.create(environment.getProperty("client.address"));
                }

                response.getHeaders().setLocation(uri);
                response.setStatusCode(HttpStatus.FOUND);
                return response.setComplete();
            }

            return chain.filter(exchange);
        };
    }

    public static class Config {

    }
}
