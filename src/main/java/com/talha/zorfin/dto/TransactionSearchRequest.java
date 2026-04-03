package com.talha.zorfin.dto;

import java.math.BigDecimal;
import java.time.Instant;

import com.talha.zorfin.enums.TransactionCategory;
import com.talha.zorfin.enums.TransactionType;

public record TransactionSearchRequest(
    String title,
    TransactionType type,
    TransactionCategory category,
    Instant startDate,
    Instant endDate,
    BigDecimal minAmount,
    BigDecimal maxAmount
) {
    
}
