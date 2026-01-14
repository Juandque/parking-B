package org.park.dtos.payments;

import org.park.model.enums.PaymentMethod;
import org.park.model.enums.PaymentStatus;

import java.util.UUID;

public record PaymentResponseDTO(
        UUID id,
        double totalAmount,
        PaymentMethod paymentMethod,
        PaymentStatus paymentStatus
) {
}
