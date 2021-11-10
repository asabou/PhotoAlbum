package com.tpjad.project.photoalbumapi.security;

import com.tpjad.project.photoalbumapi.dao.repository.IUserRepository;
import com.tpjad.project.photoalbumapi.service.impl.UserServiceImpl;
import com.tpjad.project.photoalbumapi.utils.TokenUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    private final UserServiceImpl userService;
    private final IUserRepository userRepository;
    private final JwtConfig jwtConfig;
    private final PasswordEncoder passwordEncoder;
    private final TokenUtils tokenUtils;

    public SecurityConfiguration(UserServiceImpl userService,
                                 IUserRepository userRepository, JwtConfig jwtConfig,
                                 PasswordEncoder passwordEncoder, TokenUtils tokenUtils) {
        this.userService = userService;
        this.userRepository = userRepository;
        this.jwtConfig = jwtConfig;
        this.passwordEncoder = passwordEncoder;
        this.tokenUtils = tokenUtils;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(authenticationProvider());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()//token authentication
                .cors().disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilter(new JwtAuthenticationFilter(jwtConfig, authenticationManager()))
                .addFilter(new JwtAuthorizationFilter(authenticationManager(), userRepository, jwtConfig, tokenUtils))
                .authorizeRequests()
                .antMatchers("/h2-console/**").anonymous()
                .anyRequest()
                .authenticated()
                .and()
                .logout();

        http
                .exceptionHandling()
                .authenticationEntryPoint(unauthorizedEntryPointHandler()) //for unauthorized entry point (error 401)
                .accessDeniedHandler(accessDeniedHandler()); //for access denied entry point (error 403)

        http
                .headers().frameOptions().disable(); //for h2-console
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder);
        provider.setUserDetailsService(userService);
        return provider;
    }

    @Bean
    public WebMvcConfigurer configurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedMethods("*");
            }
        };
    }

    @Bean
    public CustomUnauthorizedEntryPointHandler unauthorizedEntryPointHandler() {
        return new CustomUnauthorizedEntryPointHandler();
    }

    @Bean
    public CustomAccessDeniedHandler accessDeniedHandler() {
        return new CustomAccessDeniedHandler();
    }
}
