package com.talha.zorfin.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.talha.zorfin.dto.AuthResponseDto;
import com.talha.zorfin.dto.LoginDto;
import com.talha.zorfin.security.CustomUserDetailsService;
import com.talha.zorfin.security.JwtService;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authManager;
    private final CustomUserDetailsService userDetailsService;
    private final JwtService jwtService;

    @PostMapping("/login")
    @Operation(summary = "User login", description = "Authenticates a user and returns a JWT token.")
    public ResponseEntity<AuthResponseDto> authenticateUser(@Valid @RequestBody LoginDto loginDto) {

        // 1. Authenticate the user (Checks password)
        authManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDto.getEmail(),
                        loginDto.getPassword()));

        // 2. Load the user details
        UserDetails userDetails = userDetailsService.loadUserByUsername(loginDto.getEmail());

        // 3. Generate the token
        String jwtToken = jwtService.generateToken(userDetails);

        // 4. Return it to the client
        return ResponseEntity.ok(new AuthResponseDto(jwtToken));
    }
}