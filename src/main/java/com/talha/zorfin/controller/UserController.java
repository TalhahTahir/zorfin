package com.talha.zorfin.controller;

import org.springframework.web.bind.annotation.RestController;

import com.talha.zorfin.dto.UserDto;
import com.talha.zorfin.dto.UserRegisterDto;
import com.talha.zorfin.enums.UserRole;
import com.talha.zorfin.enums.UserStatus;
import com.talha.zorfin.service.UserService;

import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
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
    
    @PostMapping("register")
    public UserDto registerUser(@RequestBody UserRegisterDto dto) {
        return userService.createUser(dto);
    }
    
    @GetMapping("/all")
    public List<UserDto> getAllUsers() {
        return userService.getAllUsers();
    }
    
    @PutMapping("update/{id}")
    public UserDto updateUser(@PathVariable Long id, @RequestBody UserRegisterDto dto) {
        return userService.updateUser(id, dto);
    }

    @DeleteMapping("delete/{id}")
    public void deleteUser(@PathVariable Long id) { 
        userService.deleteUser(id);
    }

    @GetMapping("by-role")
    public List<UserDto> getUsersByRole(@RequestParam UserRole role) {
        return userService.getUsersByRole(role);
    }

    @GetMapping("by-status")
    public List<UserDto> getUsersByStatus(@RequestParam UserStatus status) {
        return userService.getUsersByStatus(status);
    }

    @GetMapping("by-role-and-status")
    public List<UserDto> getUsersByRoleAndStatus(@RequestParam UserRole role, @RequestParam UserStatus status) {
        return userService.getUsersByRoleAndStatus(role, status);
        // http://localhost:8080/api/users/by-role-and-status?role=ADMIN&status=ACTIVE 
    }
    
}
