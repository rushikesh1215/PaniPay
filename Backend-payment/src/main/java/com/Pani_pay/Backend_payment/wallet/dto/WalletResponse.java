package com.Pani_pay.Backend_payment.wallet.dto;

import java.math.BigDecimal;

public class WalletResponse {
    public BigDecimal balance;

    public WalletResponse(BigDecimal balance) {
        this.balance = balance;
    }
}
