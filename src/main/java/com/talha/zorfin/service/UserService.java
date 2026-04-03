package com.talha.zorfin.service;

import java.util.List;

import com.talha.zorfin.dto.UserDto;
import com.talha.zorfin.dto.UserRegisterDto;
import com.talha.zorfin.enums.UserRole;
import com.talha.zorfin.enums.UserStatus;

public interface UserService {

    // Basic CRUD
    UserDto createUser(UserRegisterDto userDto);

    List<UserDto> getUsers(
        String name, String email, UserRole role, UserStatus status
    );

    UserDto getUserById(Long id);

    UserDto updateUser(Long id, UserRegisterDto userDto);

    void deleteUser(Long id);

}
