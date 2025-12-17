package com.Pani_pay.Backend_payment.admin.service;

import com.Pani_pay.Backend_payment.auth.entity.User;
import com.Pani_pay.Backend_payment.auth.repository.UserRepository;
import com.Pani_pay.Backend_payment.transaction.entity.Transaction;
import com.Pani_pay.Backend_payment.transaction.repository.TransactionRepository;
import com.Pani_pay.Backend_payment.wallet.entity.Wallet;
import com.Pani_pay.Backend_payment.wallet.repository.WalletRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class AdminService {

    private final UserRepository userRepository;
    private final WalletRepository walletRepository;
    private final TransactionRepository transactionRepository;

    public AdminService(UserRepository userRepository,
                        WalletRepository walletRepository,
                        TransactionRepository transactionRepository) {
        this.userRepository = userRepository;
        this.walletRepository = walletRepository;
        this.transactionRepository = transactionRepository;
    }

    // ✅ PAGINATED USER LIST
    public Page<User> getAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    public Wallet getUserWallet(Long userId) {
        return walletRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Wallet not found"));
    }

    public List<Transaction> getUserTransactions(Long userId) {
        return transactionRepository.findByUserId(userId, Pageable.unpaged()).getContent();
    }

    public BigDecimal getUserTotalSpend(Long userId) {
        return transactionRepository.findByUserId(userId, Pageable.unpaged())
                .stream()
                .filter(t -> "DEBIT".equals(t.getType()))
                .map(Transaction::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
