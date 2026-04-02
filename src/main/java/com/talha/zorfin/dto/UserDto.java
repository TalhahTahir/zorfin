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
public class UserDto {
    private Long id;
    private String name;
    private String email;
    private UserRole role;
    private UserStatus status;
    private Instant createdAt;
    private Instant updatedAt;
}
