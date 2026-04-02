package com.talha.zorfin.dto;

import java.math.BigDecimal;
import java.time.Instant;

import com.talha.zorfin.enums.TransactionCategory;
import com.talha.zorfin.enums.TransactionType;

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
public class TransactionDto {
    private Long id;
    private String title;
    private BigDecimal amount;
    private TransactionType type;
    private TransactionCategory category;
    private String description;
    private Instant createdAt;
    private Instant updatedAt;
}
