package com.talha.zorfin.dto;

import java.math.BigDecimal;

import com.talha.zorfin.enums.TransactionType;

public record TypeStatsDto(
    TransactionType type,
    Long count,
    BigDecimal totalAmount,
    Double averageAmount,
    BigDecimal minAmount,
    BigDecimal maxAmount
) { }
