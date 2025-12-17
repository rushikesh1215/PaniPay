package com.Pani_pay.Backend_payment.auth.repository;

import com.Pani_pay.Backend_payment.auth.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByMobileNumber(String mobileNumber);

    boolean existsByMobileNumber(String mobileNumber);
}
