package com.talha.zorfin.dto;
import com.talha.zorfin.enums.UserRole;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RoleUpdateDto {
    @NotNull(message = "Role cannot be null")
    private UserRole role;
}
