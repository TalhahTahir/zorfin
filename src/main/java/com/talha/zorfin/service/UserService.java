package com.talha.zorfin.service;

import com.talha.zorfin.dto.PagedResponse;
import com.talha.zorfin.dto.RoleUpdateDto;
import com.talha.zorfin.dto.StatusUpdateDto;
import com.talha.zorfin.dto.UserDto;
import com.talha.zorfin.dto.UserRegisterDto;
import com.talha.zorfin.dto.UserSearchRequest;

public interface UserService {

    // Basic CRUD
    UserDto createUser(UserRegisterDto userDto);

    PagedResponse<UserDto> getUsers(UserSearchRequest request, int page, int size, String sortBy, String sortDir);

    UserDto getUserById(Long id);

    UserDto updateUser(Long id, UserRegisterDto userDto);

    void deleteUser(Long id);

    UserDto updateUserRole(Long id, RoleUpdateDto dto);

    UserDto updateUserStatus(Long id, StatusUpdateDto dto);

}
