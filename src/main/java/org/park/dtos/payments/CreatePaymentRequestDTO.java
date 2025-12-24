package org.park.dtos.payments;

import org.park.model.enums.PaymentMethod;

import java.util.UUID;

public record CreatePaymentRequestDTO(
        UUID occupancyId,
        PaymentMethod paymentMethod
) {
}
