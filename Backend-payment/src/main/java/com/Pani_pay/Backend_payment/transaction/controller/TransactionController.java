package com.Pani_pay.Backend_payment.transaction.controller;

import com.Pani_pay.Backend_payment.security.SecurityUtil;
import com.Pani_pay.Backend_payment.transaction.dto.TransactionResponse;
import com.Pani_pay.Backend_payment.transaction.service.TransactionService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transactions")
@Tag(name = "Transactions", description = "Endpoints for fetching payment history and receipts")
@SecurityRequirement(name = "bearerAuth")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping
    public Page<TransactionResponse> getTransactions(Pageable pageable) {
        Long userId = SecurityUtil.getCurrentUserId();
        return transactionService.getUserTransactions(userId, pageable)
                .map(t -> new TransactionResponse(
                        t.getId(),
                        t.getAmount(),
                        t.getType(),
                        t.getCounterpartyName(),
                        t.getCreatedAt()
                ));
    }
}
