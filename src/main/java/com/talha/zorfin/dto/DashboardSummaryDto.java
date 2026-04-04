package com.talha.zorfin.dto;

import java.math.BigDecimal;
import java.util.List;

public record DashboardSummaryDto(
    BigDecimal totalIncome,
    BigDecimal totalExpense,
    BigDecimal netBalance,
    List<TypeStatsDto> typeStats,
    List<CategoryStatsDto> categoryStats
) {
    
}
