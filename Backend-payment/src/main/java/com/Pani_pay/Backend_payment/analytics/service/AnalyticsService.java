package com.Pani_pay.Backend_payment.analytics.service;

import com.Pani_pay.Backend_payment.analytics.repository.AnalyticsRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class AnalyticsService {

    private final AnalyticsRepository analyticsRepository;

    public AnalyticsService(AnalyticsRepository analyticsRepository) {
        this.analyticsRepository = analyticsRepository;
    }

    public BigDecimal getMonthlySpending(Long userId, int year, int month) {
        return analyticsRepository.getTotalSpending(userId, year, month);
    }
}
