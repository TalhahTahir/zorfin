package com.talha.zorfin.dto;

import java.time.Instant;

import com.talha.zorfin.enums.UserRole;
import com.talha.zorfin.enums.UserStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserRegisterDto {
    private String name;
    private String email;
    private String password;
    private UserRole role;
    private UserStatus status;
    private Instant createdAt;
}
