package com.talha.zorfin.dto;
import com.talha.zorfin.enums.UserStatus;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class StatusUpdateDto {
    @NotNull(message = "Status cannot be null")
    private UserStatus status;
}
