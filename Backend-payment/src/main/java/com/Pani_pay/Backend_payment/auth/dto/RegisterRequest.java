package com.Pani_pay.Backend_payment.auth.dto;


import jakarta.validation.constraints.NotBlank;

public class RegisterRequest {

    @NotBlank
    public String mobile;

    @NotBlank
    public String name;

    @NotBlank
    public String password;
}

