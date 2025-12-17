package com.Pani_pay.Backend_payment.admin.controller;

import com.Pani_pay.Backend_payment.admin.repository.AdminAnalyticsRepository;
import com.Pani_pay.Backend_payment.admin.service.AdminService;
import com.Pani_pay.Backend_payment.auth.dto.UserResponse;
import com.Pani_pay.Backend_payment.security.SecurityUtil;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')")
@Tag(name = "System Administration", description = "High-privilege operations for platform admins")
@SecurityRequirement(name = "bearerAuth")
public class AdminController {

    private final AdminService adminService;
    private final AdminAnalyticsRepository analyticsRepository;

    public AdminController(AdminService adminService,
                           AdminAnalyticsRepository analyticsRepository) {
        this.adminService = adminService;
        this.analyticsRepository = analyticsRepository;
    }

    @GetMapping("/users")
    public Page<UserResponse> getUsers(Pageable pageable) {
        return adminService.getAllUsers(pageable)
                .map(u -> new UserResponse(
                        u.getId(),
                        u.getName(),
                        u.getMobileNumber(),
                        u.getRole()
                ));
    }

    @GetMapping("/users/{userId}/total-spend")
    public BigDecimal totalSpend(@PathVariable Long userId) {
        return adminService.getUserTotalSpend(userId);
    }

    @GetMapping("/analytics/users/{userId}/spending/monthly")
    public List<Object[]> monthly(@PathVariable Long userId,
                                  @RequestParam int year) {
        return analyticsRepository.getMonthlySpending(userId, year);
    }
}
