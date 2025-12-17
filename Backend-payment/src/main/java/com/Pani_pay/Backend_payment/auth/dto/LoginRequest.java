package com.Pani_pay.Backend_payment.auth.dto;

import jakarta.validation.constraints.NotBlank;

public class LoginRequest {

    @NotBlank
    public String mobile;

    @NotBlank
    public String password;
}
