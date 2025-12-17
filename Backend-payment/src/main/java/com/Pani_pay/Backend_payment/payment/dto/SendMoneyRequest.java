package com.Pani_pay.Backend_payment.payment.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public class SendMoneyRequest {

    @NotBlank
    public String toMobile;

    @NotNull
    public BigDecimal amount;
}

