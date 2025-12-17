package com.Pani_pay.Backend_payment.reward.dto;

import java.math.BigDecimal;

public class RewardResponse {
    public Long id;
    public BigDecimal amount;
    public String status;

    public RewardResponse(Long id, BigDecimal amount, String status) {
        this.id = id;
        this.amount = amount;
        this.status = status;
    }
}
