package com.Pani_pay.Backend_payment.auth.controller;

import com.Pani_pay.Backend_payment.auth.dto.LoginRequest;
import com.Pani_pay.Backend_payment.auth.dto.LoginResponse;
import com.Pani_pay.Backend_payment.auth.dto.RegisterRequest;
import com.Pani_pay.Backend_payment.auth.entity.User;
import com.Pani_pay.Backend_payment.auth.service.AuthService;
import com.Pani_pay.Backend_payment.security.JwtUtil;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@Tag(name = "Authentication", description = "Public endpoints for Login and Signup")
@SecurityRequirement(name = "none")
public class AuthController {

    private final AuthService authService;
    private final JwtUtil jwtUtil;

    public AuthController(AuthService authService, JwtUtil jwtUtil) {
        this.authService = authService;
        this.jwtUtil = jwtUtil;
    }

    // ✅ REGISTER (no JWT returned)
    @PostMapping("/register")
    public String register(@Valid @RequestBody RegisterRequest request) {
        try {
            authService.register(
                    request.mobile,
                    request.name,
                    request.password
            );
            return "User registered successfully";
        } catch (Exception ex) {
            return ex.getMessage();
        }
    }

    // ✅ LOGIN (JWT returned)
    @PostMapping("/login")
    public LoginResponse login(@Valid @RequestBody LoginRequest request) {
        try {
            User user = authService.login(request.mobile, request.password);
            String token = jwtUtil.generateToken(user.getId(), user.getRole());
            return new LoginResponse(token);
        } catch (Exception ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }
}
