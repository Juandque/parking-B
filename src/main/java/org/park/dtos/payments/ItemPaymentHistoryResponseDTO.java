package org.park.dtos.payments;

import org.park.model.enums.PaymentMethod;
import org.park.model.enums.PaymentStatus;

import java.util.UUID;

public record ItemPaymentHistoryResponseDTO(
        UUID paymentId,
        String userName,
        String licensePlate,
        PaymentMethod paymentMethod,
        PaymentStatus paymentStatus,
        double totalPrice
) {
}
