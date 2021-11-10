package com.tpjad.project.photoalbumapi.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.google.common.base.Strings;
import com.tpjad.project.photoalbumapi.dao.entity.UserEntity;
import com.tpjad.project.photoalbumapi.dao.repository.IUserRepository;
import com.tpjad.project.photoalbumapi.utils.TokenUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {
    private final IUserRepository userRepository;
    private final JwtConfig jwtConfig;
    private final TokenUtils tokenUtils;

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager,
                                  IUserRepository userRepository,
                                  JwtConfig jwtConfig,
                                  TokenUtils tokenUtils) {
        super(authenticationManager);
        this.userRepository = userRepository;
        this.jwtConfig = jwtConfig;
        this.tokenUtils = tokenUtils;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String authorizationHeader = request.getHeader(jwtConfig.getAuthorizationHeader());

        if (Strings.isNullOrEmpty(authorizationHeader) || !authorizationHeader.startsWith(jwtConfig.getTokenPrefix())) {
            chain.doFilter(request, response);
            return;
        }
        Authentication authentication = getUsernamePasswordAuthentication(request);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(request, response);
    }

    private Authentication getUsernamePasswordAuthentication(HttpServletRequest request) {
        String token = request.getHeader(jwtConfig.getAuthorizationHeader());
        if (token != null) {
            String username = tokenUtils.getUsernameFromToken(token);
            if (username != null) {
                UserEntity user = userRepository.findUserByUsername(username);
                return new UsernamePasswordAuthenticationToken(username, null, user.getAuthorities());
            }
            return null;
        }
        return null;
    }
}
