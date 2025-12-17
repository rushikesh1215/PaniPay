package com.Pani_pay.Backend_payment.reward.repository;

import com.Pani_pay.Backend_payment.reward.entity.Reward;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RewardRepository extends JpaRepository<Reward, Long> {

    List<Reward> findByUserIdAndStatus(Long userId, String status);
}
