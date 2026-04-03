package com.talha.zorfin.dto;

import com.talha.zorfin.enums.UserRole;
import com.talha.zorfin.enums.UserStatus;

public record UserSearchRequest(
    String name,
    String email,
    UserRole role,
    UserStatus status
) {

}
