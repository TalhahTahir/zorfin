package com.talha.zorfin.dto;

import java.math.BigDecimal;
import java.time.Instant;

import com.talha.zorfin.enums.TransactionCategory;
import com.talha.zorfin.enums.TransactionType;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionDto {
    private Long id;

    @NotBlank(message = "Title is required")
    @Size(max = 100, message = "Title must be at most 100 characters")
    private String title;

    @NotNull(message = "Amount is required")
    @DecimalMin(value = "0.01", message = "Amount must be greater than zero")
    private BigDecimal amount;

    @NotNull(message = "Transaction type is required")
    private TransactionType type;

    @NotNull(message = "Transaction category is required")
    private TransactionCategory category;

    @Size(max = 200, message = "Description must be at most 200 characters")
    private String description;

    private Instant createdAt;
    private Instant updatedAt;
}
