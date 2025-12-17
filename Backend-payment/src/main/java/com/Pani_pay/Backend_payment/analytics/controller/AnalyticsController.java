package com.Pani_pay.Backend_payment.analytics.controller;

import com.Pani_pay.Backend_payment.analytics.service.AnalyticsService;
import com.Pani_pay.Backend_payment.security.SecurityUtil;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/analytics")
@Tag(name = "User Analytics", description = "Endpoints for spending insights and reports")
@SecurityRequirement(name = "bearerAuth")
public class AnalyticsController {

    private final AnalyticsService analyticsService;

    public AnalyticsController(AnalyticsService analyticsService) {
        this.analyticsService = analyticsService;
    }

    @GetMapping("/my/spending/total")
    public BigDecimal getTotalSpending(@RequestParam int year,
                                       @RequestParam int month) {
        try {
            Long userId = SecurityUtil.getCurrentUserId();
            return analyticsService.getMonthlySpending(userId, year, month);
        } catch (Exception ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }
}
