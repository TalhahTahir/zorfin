package com.talha.zorfin.service.serviceImpl;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

import org.springframework.stereotype.Service;

import com.talha.zorfin.dto.CategoryStatsDto;
import com.talha.zorfin.dto.DashboardSummaryDto;
import com.talha.zorfin.dto.TypeStatsDto;
import com.talha.zorfin.repo.TransactionRepo;
import com.talha.zorfin.service.DashboardService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DashboardServiceImpl implements DashboardService {

    private final TransactionRepo transactionRepo;

    @Override
    public DashboardSummaryDto getDashboardSummary(Instant startDate, Instant endDate) {

        List<TypeStatsDto> typeStats = transactionRepo.getTypeStats(startDate, endDate);
        List<CategoryStatsDto> categoryStats = transactionRepo.getCategoryStats(startDate, endDate);

        BigDecimal totalIncome = BigDecimal.ZERO;
        BigDecimal totalExpense = BigDecimal.ZERO;

        for (TypeStatsDto stat : typeStats) {
            if (stat.type().name().equals("INCOME")) {
                totalIncome = stat.totalAmount();
            } else if (stat.type().name().equals("EXPENSE")) {
                totalExpense = stat.totalAmount();
            }
        }

        BigDecimal currentBalance = totalIncome.subtract(totalExpense);

        return new DashboardSummaryDto(
                totalIncome,
                totalExpense,
                currentBalance,
                typeStats,
                categoryStats);
    }

}
