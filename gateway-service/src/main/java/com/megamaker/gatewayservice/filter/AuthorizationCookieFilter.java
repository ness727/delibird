package com.megamaker.gatewayservice.filter;

import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
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
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.net.URI;
import java.util.Base64;

@Slf4j
@Component
public class AuthorizationCookieFilter extends AbstractGatewayFilterFactory<AuthorizationCookieFilter.Config> {
    private final Environment environment;

    @Autowired
    public AuthorizationCookieFilter(Environment environment) {
        super(Config.class);
        this.environment = environment;
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            String authHeader = request.getHeaders().getFirst("Auth");
            MultiValueMap<String, HttpCookie> cookies = request.getCookies();

            String jwt = "";
            if (StringUtils.hasText(authHeader)) {
                log.debug("헤더");
                jwt = authHeader;
            }
            if (cookies.containsKey("Auth")) {
                jwt = cookies.getFirst("Auth").getValue();
            }
            log.debug(jwt);

            // 헤더나 쿠키에 Auth가 포함되어 있을 때
            if (StringUtils.hasText(jwt)) {
                if (!isJwtValid(jwt)) {
                    log.debug("토큰이 유효하지 않음");
                    return onError(exchange, "Token is not valid", HttpStatus.UNAUTHORIZED);
                }
            } else {
                log.debug("쿠키 없음");
                return onError(exchange, "Token is not valid", HttpStatus.UNAUTHORIZED);
            }
            return chain.filter(exchange);
        };
    }

    private boolean isJwtValid(String jwt) {
        boolean returnValue = true;
        String subject = null;

        byte[] secretKeyBytes = Base64.getEncoder().encode(environment.getProperty("token.secret").getBytes());
        SecretKey secretKey = new SecretKeySpec(secretKeyBytes, Jwts.SIG.HS256.key().build().getAlgorithm());

        try {
            JwtParser jwtParser = Jwts.parser()
                    .verifyWith(secretKey)
                    .build();
            subject = jwtParser.parseSignedClaims(jwt).getPayload().getSubject();
        } catch (Exception ex) {
            returnValue = false;
        }

        if (subject == null || subject.isEmpty()) {
            returnValue = false;
        }
        return returnValue;
    }

    private Mono<Void> onError(ServerWebExchange exchange, String err, HttpStatus httpStatus) {
        ServerHttpResponse response = exchange.getResponse();

        response.getHeaders().setLocation(URI.create(environment.getProperty("client.address")));
        response.setStatusCode(HttpStatus.FOUND);

        log.debug(err);
        return response.setComplete();
    }

    public static class Config {

    }
}
