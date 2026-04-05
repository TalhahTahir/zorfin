package com.talha.zorfin.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.talha.zorfin.security.CustomUserDetailsService;
import com.talha.zorfin.security.JwtAuthenticationFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomUserDetailsService userDetailsService;
    private final JwtAuthenticationFilter jwtAuthFilter;

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                        .requestMatchers("api/auth/**", "/api/dashboard").permitAll()
                        .anyRequest().authenticated())

                // Tell Spring Security not to store sessions
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider())
                // Add our custom JWT filter BEFORE the standard Spring username/password filter
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}

/*
 * 3. The Spring Security "Gotcha" (Pro-Tip)
 * Since you are planning ahead, I want to save you hours of debugging in the
 * future: @RestControllerAdvice only catches exceptions that happen inside your
 * Controllers (the DispatcherServlet).
 * 
 * Spring Security operates using a Filter Chain that runs before the request
 * ever reaches your Controller.
 * 
 * If a user fails RBAC checks on a method (@PreAuthorize), it throws an
 * AccessDeniedException inside the controller layer, and your
 * GlobalExceptionHandler catches it perfectly.
 * 
 * However, if a user provides an invalid JWT token, the Spring Security Filter
 * rejects it immediately. It never reaches your Controller, so
 * your @RestControllerAdvice will not catch it, and Spring will return a
 * default, unformatted 401 response.
 * 
 * How you will fix this in the future:
 * When you configure Spring Security, you will tell it to forward filter-level
 * exceptions to an AuthenticationEntryPoint and an AccessDeniedHandler, which
 * you will custom-write to return your exact ApiErrorResponse JSON structure.
 */