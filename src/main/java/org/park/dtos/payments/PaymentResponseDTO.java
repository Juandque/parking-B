package org.park.dtos.payments;

import org.park.model.enums.PaymentMethod;
import org.park.model.enums.PaymentStatus;

import java.math.BigDecimal;
import java.util.UUID;

public record PaymentResponseDTO(
        UUID id,
        BigDecimal totalAmount,
        PaymentMethod paymentMethod,
        PaymentStatus paymentStatus
) {
}
