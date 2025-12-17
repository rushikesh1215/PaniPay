package com.Pani_pay.Backend_payment.analytics.repository;

import com.Pani_pay.Backend_payment.transaction.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;

public interface AnalyticsRepository extends JpaRepository<Transaction, Long> {

    @Query("""
        SELECT COALESCE(SUM(t.amount), 0)
        FROM Transaction t
        WHERE t.userId = :userId
          AND t.type = 'DEBIT'
          AND YEAR(t.createdAt) = :year
          AND MONTH(t.createdAt) = :month
    """)
    BigDecimal getTotalSpending(  @Param("userId") Long userId,
                                  @Param("year") int year,
                                  @Param("month") int month);
}
