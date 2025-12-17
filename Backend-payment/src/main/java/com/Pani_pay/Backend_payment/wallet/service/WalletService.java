package com.Pani_pay.Backend_payment.wallet.service;


import com.Pani_pay.Backend_payment.transaction.entity.Transaction;
import com.Pani_pay.Backend_payment.transaction.repository.TransactionRepository;
import com.Pani_pay.Backend_payment.wallet.entity.Wallet;
import com.Pani_pay.Backend_payment.wallet.repository.WalletRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class WalletService {

    private final WalletRepository walletRepository;
    private final TransactionRepository transactionRepository;

    public WalletService(WalletRepository walletRepository,
                         TransactionRepository transactionRepository) {
        this.walletRepository = walletRepository;
        this.transactionRepository = transactionRepository;
    }

    public Wallet getWallet(Long userId) {
        return walletRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Wallet not found"));
    }

    @Transactional
    public void addMoney(Long userId, BigDecimal amount) {

        try {
            Wallet wallet = getWallet(userId);
            wallet.setBalance(wallet.getBalance().add(amount));
            walletRepository.save(wallet);

            Transaction tx = new Transaction();
            tx.setUserId(userId);
            tx.setAmount(amount);
            tx.setType("CREDIT");
            tx.setCounterpartyName("Add Money");

            transactionRepository.save(tx);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
