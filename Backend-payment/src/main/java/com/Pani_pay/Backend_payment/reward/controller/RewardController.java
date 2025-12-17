package com.Pani_pay.Backend_payment.reward.controller;

import com.Pani_pay.Backend_payment.reward.dto.RewardResponse;
import com.Pani_pay.Backend_payment.reward.service.RewardService;
import com.Pani_pay.Backend_payment.security.SecurityUtil;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rewards")
@Tag(name = "Rewards & Loyalty", description = "Endpoints for cashback, points, and referrals")
@SecurityRequirement(name = "bearerAuth")
public class RewardController {

    private final RewardService rewardService;

    public RewardController(RewardService rewardService) {
        this.rewardService = rewardService;
    }

    @GetMapping
    public List<RewardResponse> getRewards() {
        Long userId = SecurityUtil.getCurrentUserId();
        return rewardService.getNewRewards(userId)
                .stream()
                .map(r -> new RewardResponse(
                        r.getId(),
                        r.getAmount(),
                        r.getStatus()
                ))
                .toList();
    }

    @PostMapping("/{rewardId}/claim")
    public String claim(@PathVariable Long rewardId) {
        Long userId = SecurityUtil.getCurrentUserId();
        rewardService.claimReward(userId, rewardId);
        return "Reward claimed successfully";
    }
}
