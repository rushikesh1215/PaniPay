package com.Pani_pay.Backend_payment.reward.service;
import com.Pani_pay.Backend_payment.common.events.PaymentCompletedEvent;
import com.Pani_pay.Backend_payment.reward.entity.Reward;
import com.Pani_pay.Backend_payment.reward.repository.RewardRepository;
import com.Pani_pay.Backend_payment.transaction.entity.Transaction;
import com.Pani_pay.Backend_payment.transaction.repository.TransactionRepository;
import com.Pani_pay.Backend_payment.wallet.entity.Wallet;
import com.Pani_pay.Backend_payment.wallet.repository.WalletRepository;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.context.event.EventListener;

import java.math.BigDecimal;
import java.util.List;

@Service
public class RewardService {

    private final RewardRepository rewardRepository;
    private final WalletRepository walletRepository;
    private final TransactionRepository transactionRepository;

    public RewardService(RewardRepository rewardRepository,
                         WalletRepository walletRepository,
                         TransactionRepository transactionRepository) {
        this.rewardRepository = rewardRepository;
        this.walletRepository = walletRepository;
        this.transactionRepository = transactionRepository;
    }

    // 🔔 Async listener
    @Async
    @EventListener
    public void handlePaymentCompleted(PaymentCompletedEvent event) {

        // Simple reward rule (example)
        BigDecimal rewardAmount = BigDecimal.ZERO;

        if (event.amount().compareTo(new BigDecimal("500")) >= 0) {
            rewardAmount = new BigDecimal("50");
        }

        if (rewardAmount.compareTo(BigDecimal.ZERO) > 0) {
            Reward reward = new Reward();
            reward.setUserId(event.userId());
            reward.setPaymentId(event.paymentId());
            reward.setAmount(rewardAmount);
            reward.setStatus("NEW");

            rewardRepository.save(reward);
        }
    }

    public List<Reward> getNewRewards(Long userId) {
        return rewardRepository.findByUserIdAndStatus(userId, "NEW");
    }

    @Transactional
    public void claimReward(Long userId, Long rewardId) {

        Reward reward = rewardRepository.findById(rewardId)
                .orElseThrow(() -> new RuntimeException("Reward not found"));

        if (!reward.getUserId().equals(userId)) {
            throw new RuntimeException("Unauthorized reward access");
        }

        if (!"NEW".equals(reward.getStatus())) {
            throw new RuntimeException("Reward already claimed or expired");
        }

        Wallet wallet = walletRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Wallet not found"));

        // Credit wallet
        wallet.setBalance(wallet.getBalance().add(reward.getAmount()));
        walletRepository.save(wallet);

        // Create CREDIT transaction
        Transaction tx = new Transaction();
        tx.setUserId(userId);
        tx.setPaymentId(reward.getPaymentId());
        tx.setAmount(reward.getAmount());
        tx.setType("CREDIT");
        tx.setCounterpartyName("Cashback Reward");
        transactionRepository.save(tx);

        // Mark reward claimed
        reward.setStatus("CLAIMED");
        rewardRepository.save(reward);
    }
}
