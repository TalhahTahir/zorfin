package com.talha.zorfin.dto;

import java.math.BigDecimal;

import com.talha.zorfin.enums.TransactionCategory;

public record CategoryStatsDto(
    TransactionCategory category,
    Long count,
    BigDecimal totalAmount,
    Double averageAmount,
    BigDecimal minAmount,
    BigDecimal maxAmount
) {}
