package com.tpjad.project.photoalbumapi.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.tpjad.project.photoalbumapi.dao.entity.RoleEntity;
import com.tpjad.project.photoalbumapi.dao.entity.UserEntity;
import com.tpjad.project.photoalbumapi.utils.Base64Utils;
import com.tpjad.project.photoalbumapi.utils.Constants;
import com.tpjad.project.photoalbumapi.utils.DateUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.stream.Collectors;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final JwtConfig jwtConfig;
    private final AuthenticationManager authenticationManager;

    public JwtAuthenticationFilter(JwtConfig jwtConfig,
                                   AuthenticationManager authenticationManager) {
        this.jwtConfig = jwtConfig;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        final String authHeader = request.getHeader(jwtConfig.getAuthorizationHeader()).split(" ")[1];
        final String authStringDecoded = Base64Utils.decode(authHeader);
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                Base64Utils.getUsername(authStringDecoded),
                Base64Utils.getPassword(authStringDecoded)
        );
        return authenticationManager.authenticate(authentication);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {
        UserEntity user = (UserEntity) authResult.getPrincipal();
        String token = JWT.create()
                        .withSubject(user.getUsername())
                        .withClaim(Constants.USER_ID, user.getId())
                        .withClaim(Constants.ROLES, user.getAuthorities().stream().map(RoleEntity::getRole).collect(Collectors.toList()))
                                .withExpiresAt(new Date(DateUtils.getCurrentTimeMillsDate() + 4 * 60 * 60 * 1000))
                                        .sign(Algorithm.HMAC512(jwtConfig.getSecretKey().getBytes()));

        response.addHeader(jwtConfig.getAuthorizationHeader(), jwtConfig.getTokenPrefix() + token);
        response.addHeader("Access-Control-Expose-Headers", jwtConfig.getAuthorizationHeader());
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException {
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.getWriter().write("Login failed! Username or password incorrect!");
    }
}
