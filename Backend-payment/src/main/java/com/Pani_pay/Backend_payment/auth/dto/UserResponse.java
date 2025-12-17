package com.Pani_pay.Backend_payment.auth.dto;

public class UserResponse {
    public Long id;
    public String name;
    public String mobile;
    public String role;

    public UserResponse(Long id, String name, String mobile, String role) {
        this.id = id;
        this.name = name;
        this.mobile = mobile;
        this.role = role;
    }
}
