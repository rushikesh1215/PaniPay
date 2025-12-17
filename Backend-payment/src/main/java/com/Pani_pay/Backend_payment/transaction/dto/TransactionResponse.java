package com.Pani_pay.Backend_payment.transaction.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class TransactionResponse {
    public Long id;
    public BigDecimal amount;
    public String type;
    public String counterparty;
    public LocalDateTime createdAt;

    public TransactionResponse(Long id, BigDecimal amount, String type,
                               String counterparty, LocalDateTime createdAt) {
        this.id = id;
        this.amount = amount;
        this.type = type;
        this.counterparty = counterparty;
        this.createdAt = createdAt;
    }
}
