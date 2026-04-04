package com.talha.zorfin.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                .anyRequest().authenticated()
            )
            .httpBasic(Customizer.withDefaults());

        return http.build();
    }
}

/*
3. The Spring Security "Gotcha" (Pro-Tip)
Since you are planning ahead, I want to save you hours of debugging in the future: @RestControllerAdvice only catches exceptions that happen inside your Controllers (the DispatcherServlet).

Spring Security operates using a Filter Chain that runs before the request ever reaches your Controller.

If a user fails RBAC checks on a method (@PreAuthorize), it throws an AccessDeniedException inside the controller layer, and your GlobalExceptionHandler catches it perfectly.

However, if a user provides an invalid JWT token, the Spring Security Filter rejects it immediately. It never reaches your Controller, so your @RestControllerAdvice will not catch it, and Spring will return a default, unformatted 401 response.

How you will fix this in the future:
When you configure Spring Security, you will tell it to forward filter-level exceptions to an AuthenticationEntryPoint and an AccessDeniedHandler, which you will custom-write to return your exact ApiErrorResponse JSON structure.
*/