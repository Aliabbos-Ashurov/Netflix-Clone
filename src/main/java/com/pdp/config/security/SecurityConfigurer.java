package com.pdp.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/**
 * @author Aliabbos Ashurov
 * @since 06/August/2024  10:48
 **/
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfigurer {

    private final String[] WHITE_LIST = new String[]{
            "/auth/login",
            "/auth/signup",
            "/static/**"
    };

    private final CustomAuthhenticationFailureHandler customAuthhenticationFailureHandler;
    private final CustomUserDetailService customUserDetailService;

    @Autowired
    public SecurityConfigurer(CustomAuthhenticationFailureHandler customAuthhenticationFailureHandler, CustomUserDetailService customUserDetailService) {
        this.customAuthhenticationFailureHandler = customAuthhenticationFailureHandler;
        this.customUserDetailService = customUserDetailService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable);
        http.userDetailsService(customUserDetailService);
        http.authorizeHttpRequests(httpRequest ->
                httpRequest
                        .requestMatchers(WHITE_LIST)
                        .permitAll()
                        .requestMatchers("/main/home").hasAnyRole("USER", "ADMIN")
                        .anyRequest()
                        .fullyAuthenticated()
        );
        http.formLogin(httpSecurityFormLoginConfigurer ->
                httpSecurityFormLoginConfigurer
                        .loginPage("/auth/login")
                        .usernameParameter("username")
                        .passwordParameter("password")
                        .defaultSuccessUrl("/main/home", true)
                        .failureHandler(customAuthhenticationFailureHandler)
        );
        http.logout(httpSecurityLogoutConfigurer ->
                httpSecurityLogoutConfigurer
                        .logoutUrl("/auth/logout")
                        .deleteCookies("JSESSIONID")
                        .logoutRequestMatcher(new AntPathRequestMatcher("/auth/logout", "POST"))
                        .clearAuthentication(true)
        );
        http.rememberMe(httpSecurityRememberMeConfigurer ->
                httpSecurityRememberMeConfigurer
                        .rememberMeParameter("remember-me")
                        .rememberMeCookieName("rem-me")
                        .tokenValiditySeconds(3600)
                        .key("key$)ni7g4304g$$a")
                        .alwaysRemember(true)
                        .userDetailsService(customUserDetailService)
        );
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
