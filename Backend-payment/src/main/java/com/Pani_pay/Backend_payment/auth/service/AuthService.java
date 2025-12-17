package com.Pani_pay.Backend_payment.auth.service;

import com.Pani_pay.Backend_payment.auth.entity.User;
import com.Pani_pay.Backend_payment.auth.repository.UserRepository;
import com.Pani_pay.Backend_payment.wallet.entity.Wallet;
import com.Pani_pay.Backend_payment.wallet.repository.WalletRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final WalletRepository walletRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UserRepository userRepository,
                       WalletRepository walletRepository,
                       PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.walletRepository = walletRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public void register(String mobile, String name, String password) {

        if (userRepository.existsByMobileNumber(mobile)) {
            throw new RuntimeException("Mobile number already registered");
        }

        User user = new User();
        user.setMobileNumber(mobile);
        user.setName(name);
        user.setPassword(passwordEncoder.encode(password));
        user.setRole("USER");

        User savedUser = userRepository.save(user);

        Wallet wallet = new Wallet();
        wallet.setUserId(savedUser.getId());

        walletRepository.save(wallet);
    }

    public User login(String mobile, String password) {

        User user = userRepository.findByMobileNumber(mobile)
                .orElseThrow(() -> new RuntimeException("Invalid credentials"));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        return user; // gateway will generate JWT
    }
}

