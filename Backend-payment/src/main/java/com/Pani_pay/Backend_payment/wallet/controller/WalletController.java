package com.Pani_pay.Backend_payment.wallet.controller;

import com.Pani_pay.Backend_payment.security.SecurityUtil;
import com.Pani_pay.Backend_payment.wallet.dto.WalletResponse;
import com.Pani_pay.Backend_payment.wallet.service.WalletService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/wallet")
@Tag(name = "Wallet Management", description = "Endpoints for managing user digital wallets")
@SecurityRequirement(name = "bearerAuth")
public class WalletController {

    private final WalletService walletService;

    public WalletController(WalletService walletService) {
        this.walletService = walletService;
    }

    @GetMapping("/balance")
    public WalletResponse getBalance() {
        Long userId = SecurityUtil.getCurrentUserId();
        return new WalletResponse(
                walletService.getWallet(userId).getBalance()
        );
    }

    @PostMapping("/add")
    public String addMoney(@RequestParam BigDecimal amount) {
        Long userId = SecurityUtil.getCurrentUserId();
        walletService.addMoney(userId, amount);
        return "Money added successfully";
    }
}
