package org.park.dtos.payments;

import org.park.model.enums.PaymentMethod;
import org.park.model.enums.PaymentStatus;

import java.math.BigDecimal;
import java.util.UUID;

public record PaymentDetailResponseDTO(
        UUID paymentId,
        String userName,
        String licensePlate,
        PaymentMethod paymentMethod,
        PaymentStatus paymentStatus,
        BigDecimal totalPrice
) {
}
