package org.park.dtos.occupancies;

import org.park.model.enums.FeeType;
import org.park.model.enums.PaymentMethod;
import org.park.model.enums.PaymentStatus;
import org.park.model.enums.VehicleType;

import java.time.LocalDateTime;
import java.util.UUID;

public record OccupancyDetailResponseDTO(
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
        double feeApplied,
        PaymentMethod paymentMethod,
        LocalDateTime paymentDate,
        PaymentStatus paymentStatus,
        double totalPrice
) {
}
