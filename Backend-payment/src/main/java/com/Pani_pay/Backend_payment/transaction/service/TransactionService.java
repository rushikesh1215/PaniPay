package com.Pani_pay.Backend_payment.transaction.service;

import com.Pani_pay.Backend_payment.transaction.entity.Transaction;
import com.Pani_pay.Backend_payment.transaction.repository.TransactionRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;

    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public Page<Transaction> getUserTransactions(Long userId, Pageable pageable) {
        return transactionRepository.findByUserId(userId, pageable);
    }
}
