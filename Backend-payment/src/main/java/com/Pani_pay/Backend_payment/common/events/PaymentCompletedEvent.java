package com.Pani_pay.Backend_payment.common.events;

import java.math.BigDecimal;

public record PaymentCompletedEvent(
        Long paymentId,
        Long userId,
        BigDecimal amount
) {}
