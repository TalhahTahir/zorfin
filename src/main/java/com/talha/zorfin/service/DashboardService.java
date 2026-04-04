package com.talha.zorfin.service;

import java.time.Instant;

import com.talha.zorfin.dto.DashboardSummaryDto;

public interface DashboardService {
    
    DashboardSummaryDto getDashboardSummary(Instant startDate, Instant endDate);
}
