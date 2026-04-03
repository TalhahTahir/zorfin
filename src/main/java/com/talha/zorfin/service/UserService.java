package com.talha.zorfin.service;

import java.util.List;

import com.talha.zorfin.dto.UserDto;
import com.talha.zorfin.dto.UserRegisterDto;
import com.talha.zorfin.enums.UserRole;
import com.talha.zorfin.enums.UserStatus;

public interface UserService {

    // Basic CRUD
    UserDto createUser(UserRegisterDto userDto);

    List<UserDto> getAllUsers();

    UserDto getUserById(Long id);

    UserDto updateUser(Long id, UserRegisterDto userDto);

    void deleteUser(Long id);

    // Filter methods
    List<UserDto> getUsersByRole(UserRole role);

    List<UserDto> getUsersByStatus(UserStatus status);

    List<UserDto> getUsersByRoleAndStatus(UserRole role, UserStatus status);
}
