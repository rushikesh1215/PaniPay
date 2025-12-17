package com.Pani_pay.Backend_payment.payment.service;

import com.Pani_pay.Backend_payment.auth.entity.User;
import com.Pani_pay.Backend_payment.auth.repository.UserRepository;
import com.Pani_pay.Backend_payment.common.events.PaymentCompletedEvent;
import com.Pani_pay.Backend_payment.payment.entity.Payment;
import com.Pani_pay.Backend_payment.payment.repository.PaymentRepository;
import com.Pani_pay.Backend_payment.transaction.entity.Transaction;
import com.Pani_pay.Backend_payment.transaction.repository.TransactionRepository;
import com.Pani_pay.Backend_payment.wallet.entity.Wallet;
import com.Pani_pay.Backend_payment.wallet.repository.WalletRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;


@Service
public class PaymentService {

    private final UserRepository userRepository;
    private final WalletRepository walletRepository;
    private final PaymentRepository paymentRepository;
    private final TransactionRepository transactionRepository;
    private final ApplicationEventPublisher eventPublisher;

    public PaymentService(UserRepository userRepository,
                          WalletRepository walletRepository,
                          PaymentRepository paymentRepository,
                          TransactionRepository transactionRepository,
                          ApplicationEventPublisher eventPublisher) {
        this.userRepository = userRepository;
        this.walletRepository = walletRepository;
        this.paymentRepository = paymentRepository;
        this.transactionRepository = transactionRepository;
        this.eventPublisher = eventPublisher;
    }

    @Transactional
    public void sendMoney(Long senderUserId, String receiverMobile, BigDecimal amount) {

        try {
            if (amount.compareTo(BigDecimal.ZERO) <= 0) {
                throw new RuntimeException("Amount must be greater than zero");
            }

            Wallet senderWallet = walletRepository.findByUserId(senderUserId)
                    .orElseThrow(() -> new RuntimeException("Sender wallet not found"));

            if (senderWallet.getBalance().compareTo(amount) < 0) {
                throw new RuntimeException("Insufficient balance");
            }

            User receiver = userRepository.findByMobileNumber(receiverMobile)
                    .orElseThrow(() -> new RuntimeException("Receiver not found"));

            Wallet receiverWallet = walletRepository.findByUserId(receiver.getId())
                    .orElseThrow(() -> new RuntimeException("Receiver wallet not found"));

            Payment payment = new Payment();
            payment.setSenderUserId(senderUserId);
            payment.setReceiverUserId(receiver.getId());
            payment.setAmount(amount);
            payment.setStatus("INITIATED");

            payment = paymentRepository.save(payment);

            // Debit sender
            senderWallet.setBalance(senderWallet.getBalance().subtract(amount));
            walletRepository.save(senderWallet);

            Transaction debitTx = new Transaction();
            debitTx.setUserId(senderUserId);
            debitTx.setPaymentId(payment.getId());
            debitTx.setAmount(amount);
            debitTx.setType("DEBIT");
            debitTx.setCounterpartyName(receiver.getName());
            transactionRepository.save(debitTx);

            // Credit receiver
            receiverWallet.setBalance(receiverWallet.getBalance().add(amount));
            walletRepository.save(receiverWallet);

            Transaction creditTx = new Transaction();
            creditTx.setUserId(receiver.getId());
            creditTx.setPaymentId(payment.getId());
            creditTx.setAmount(amount);
            creditTx.setType("CREDIT");
            creditTx.setCounterpartyName("From " + senderUserId);
            transactionRepository.save(creditTx);

            payment.setStatus("SUCCESS");
            paymentRepository.save(payment);

            // 🔔 Publish event (NO reward logic here)
            eventPublisher.publishEvent(
                    new PaymentCompletedEvent(payment.getId(), senderUserId, amount)
            );

        } catch (Exception ex) {
            throw new RuntimeException("Payment failed: " + ex.getMessage());
        }
    }
}
