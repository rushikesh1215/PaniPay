package com.Pani_pay.Backend_payment.payment.controller;

import com.Pani_pay.Backend_payment.payment.dto.SendMoneyRequest;
import com.Pani_pay.Backend_payment.payment.service.PaymentService;
import com.Pani_pay.Backend_payment.security.SecurityUtil;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payment")
@Tag(name = "Payments", description = "Endpoints for processing transfers and checkouts")
@SecurityRequirement(name = "bearerAuth")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/send")
    public String sendMoney(@Valid @RequestBody SendMoneyRequest request) {
        try {
            Long userId = SecurityUtil.getCurrentUserId();
            paymentService.sendMoney(userId, request.toMobile, request.amount);
            return "Payment successful";
        } catch (Exception ex) {
            return ex.getMessage();
        }
    }
}
