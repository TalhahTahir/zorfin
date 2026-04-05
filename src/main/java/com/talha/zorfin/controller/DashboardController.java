package com.talha.zorfin.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.talha.zorfin.dto.DashboardSummaryDto;
import com.talha.zorfin.service.DashboardService;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import java.time.Instant;

@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final DashboardService dashboardService;

@PreAuthorize("hasAnyRole('USER', 'ANALYST', 'ADMIN')")
    @GetMapping
    @Operation(summary = "Get dashboard summary", description = "Returns aggregated dashboard metrics for the optional date range.")
    public DashboardSummaryDto getSummary(
            @RequestParam (required = false) Instant startDate,
            @RequestParam (required = false) Instant endDate) {
            
        return dashboardService.getDashboardSummary(startDate, endDate);
    }
}