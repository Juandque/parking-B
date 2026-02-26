package org.park.dtos.occupancies;

import org.park.model.enums.FeeType;
import org.park.model.enums.VehicleType;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record OccupancyInfoPaymentResponseDTO(
        UUID occupancyId,
        UUID parkingSlotId,
        UUID userId,
        UUID vehicleId,
        UUID feeId,
        UUID paymentId,
        String userName,
        String userDocument,
        String userPhone,
        String userEmail,
        String licensePlate,
        VehicleType vehicleType,
        FeeType feeType,
        String parkingSlotNumber,
        LocalDateTime startDate,
        LocalDateTime endDate,
        BigDecimal feeApplied,
        BigDecimal totalPrice
) {
}
