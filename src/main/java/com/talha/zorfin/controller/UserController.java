package com.talha.zorfin.controller;

import org.springframework.web.bind.annotation.RestController;

import com.talha.zorfin.dto.UserDto;
import com.talha.zorfin.dto.UserRegisterDto;
import com.talha.zorfin.dto.UserSearchRequest;
import com.talha.zorfin.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('ANALYST', 'ADMIN')") // Both ANALYST and ADMIN can access user endpoints
@RequestMapping("/api/users")
public class UserController {
    
    private final UserService userService;

    @PreAuthorize("hasRole('ADMIN')") // Only ADMIN can register new users
    @PostMapping
    public UserDto registerUser(@Valid @RequestBody UserRegisterDto dto) {
        return userService.createUser(dto);
    }

    @GetMapping("{id}")
    public UserDto getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @GetMapping
    public List<UserDto> getUsers(UserSearchRequest request) {
        return userService.getUsers(request);
    }
    
    @PreAuthorize("hasRole('ADMIN')") // Only ADMIN can update users
    @PutMapping("/{id}")
    public UserDto updateUser(@PathVariable Long id, @Valid @RequestBody UserRegisterDto dto) {
        return userService.updateUser(id, dto);
    }

    @PreAuthorize("hasRole('ADMIN')") // Only ADMIN can delete users
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) { 
        userService.deleteUser(id);
    }
}
