package com.Pani_pay.Backend_payment.admin.repository;

import com.Pani_pay.Backend_payment.transaction.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AdminAnalyticsRepository extends JpaRepository<Transaction, Long> {

    @Query("""
        SELECT MONTH(t.createdAt), SUM(t.amount)
        FROM Transaction t
        WHERE t.userId = :userId
          AND t.type = 'DEBIT'
          AND YEAR(t.createdAt) = :year
        GROUP BY MONTH(t.createdAt)
        ORDER BY MONTH(t.createdAt)
    """)
    List<Object[]> getMonthlySpending(Long userId, int year);
}
