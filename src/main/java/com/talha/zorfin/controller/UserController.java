package com.talha.zorfin.controller;

import org.springframework.web.bind.annotation.RestController;

import com.talha.zorfin.dto.UserDto;
import com.talha.zorfin.dto.UserRegisterDto;
import com.talha.zorfin.dto.UserSearchRequest;
import com.talha.zorfin.service.UserService;

import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {
    
    private final UserService userService;

    @GetMapping("{id}")
    public UserDto getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @GetMapping
    public List<UserDto> getUsers(UserSearchRequest request) {
        return userService.getUsers(request);
    }
    
    @PostMapping
    public UserDto registerUser(@RequestBody UserRegisterDto dto) {
        return userService.createUser(dto);
    }
    
    @PutMapping("/{id}")
    public UserDto updateUser(@PathVariable Long id, @RequestBody UserRegisterDto dto) {
        return userService.updateUser(id, dto);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) { 
        userService.deleteUser(id);
    }
}
