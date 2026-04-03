package com.talha.zorfin.service;

import java.util.List;

import com.talha.zorfin.dto.UserDto;
import com.talha.zorfin.dto.UserRegisterDto;
import com.talha.zorfin.dto.UserSearchRequest;

public interface UserService {

    // Basic CRUD
    UserDto createUser(UserRegisterDto userDto);

    List<UserDto> getUsers(UserSearchRequest request);

    UserDto getUserById(Long id);

    UserDto updateUser(Long id, UserRegisterDto userDto);

    void deleteUser(Long id);

}
