package org.park.dtos.occupancies;

import org.park.model.enums.FeeType;
import org.park.model.enums.VehicleType;

import java.util.UUID;

public record OccupancyInfoUpdateResponseDTO(
        UUID occupancyId,
        UUID parkingSlotId,
        UUID userId,
        UUID vehicleId,
        UUID feeId,
        String userName,
        String userDocument,
        String userPhone,
        String userEmail,
        String licensePlate,
        VehicleType vehicleType,
        FeeType feeType,
        String parkingSlotNumber
) {
}
