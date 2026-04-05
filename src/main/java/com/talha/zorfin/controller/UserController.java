package com.talha.zorfin.controller;

import org.springframework.web.bind.annotation.RestController;

import com.talha.zorfin.dto.PagedResponse;
import com.talha.zorfin.dto.RoleUpdateDto;
import com.talha.zorfin.dto.StatusUpdateDto;
import com.talha.zorfin.dto.UserDto;
import com.talha.zorfin.dto.UserRegisterDto;
import com.talha.zorfin.dto.UserSearchRequest;
import com.talha.zorfin.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    @Operation(summary = "Register user", description = "Creates a new user account.")
    public UserDto registerUser(@Valid @RequestBody UserRegisterDto dto) {
        return userService.createUser(dto);
    }

    @GetMapping("{id}")
    @Operation(summary = "Get user by ID", description = "Fetches a user by their unique ID.")
    public UserDto getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @GetMapping    
    @Operation(summary = "List users", description = "Returns a paginated list of users with optional filters and sorting.")
    public PagedResponse<UserDto> getUsers(
            UserSearchRequest request, // Your existing filter DTO
            @RequestParam(value = "page", defaultValue = "0", required = false) int page,
            @RequestParam(value = "size", defaultValue = "10", required = false) int size,
            @RequestParam(value = "sortBy", defaultValue = "id", required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir) {
        return userService.getUsers(request, page, size, sortBy, sortDir);
    }

    @PreAuthorize("hasRole('ADMIN')") // Only ADMIN can update users
    @PutMapping("/{id}")
    @Operation(summary = "Update user", description = "Updates an existing user by ID.")
    public UserDto updateUser(@PathVariable Long id, @Valid @RequestBody UserRegisterDto dto) {
        return userService.updateUser(id, dto);
    }

    @PreAuthorize("hasRole('ADMIN')") // Only ADMIN can delete users
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete user", description = "Deletes a user by ID.")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }

    @PatchMapping("/{id}/role")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Update user role", description = "Allows an Admin to promote/demote a user.")
    public UserDto updateUserRole(@PathVariable Long id, @Valid @RequestBody RoleUpdateDto dto) {
        return userService.updateUserRole(id, dto);
    }

    @PatchMapping("/{id}/status")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Update user status", description = "Allows an Admin to activate or deactivate a user.")
    public UserDto updateUserStatus(@PathVariable Long id, @Valid @RequestBody StatusUpdateDto dto) {
        return userService.updateUserStatus(id, dto);
    }
}