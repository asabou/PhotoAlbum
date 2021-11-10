package com.tpjad.project.photoalbumapi.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.tpjad.project.photoalbumapi.security.JwtConfig;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TokenUtils {
    private final JwtConfig jwtConfig;

    public TokenUtils(JwtConfig jwtConfig) {
        this.jwtConfig = jwtConfig;
    }

    public String getUsernameFromToken(String token) {
        return JWT.require(Algorithm.HMAC512(jwtConfig.getSecretKey().getBytes()))
                .build()
                .verify(token.replace(jwtConfig.getTokenPrefix(), ""))
                .getSubject();
    }

    public Long getUserIdFromToken(String token) {
        return JWT.require(Algorithm.HMAC512(jwtConfig.getSecretKey().getBytes()))
                .build()
                .verify(token.replace(jwtConfig.getTokenPrefix(), ""))
                .getClaim(Constants.USER_ID).asLong();
    }

    public List<String> getRolesFromToken(String token) {
        return JWT.require(Algorithm.HMAC512(jwtConfig.getSecretKey().getBytes()))
                .build()
                .verify(token.replace(jwtConfig.getTokenPrefix(), ""))
                .getClaim(Constants.ROLES).asList(String.class);
    }
}
