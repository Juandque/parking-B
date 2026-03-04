package org.park.dtos.payments;

public record PaymentConfirmedResponseDTO(
        PaymentDetailResponseDTO paymentInfo,
        byte[] pdfContent
) {
}
