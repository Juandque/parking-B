package org.park.dtos.occupancies;

import org.park.model.enums.ParkingSlotType;
import org.park.model.enums.VehicleType;

import java.time.LocalDateTime;
import java.util.UUID;

public record ItemHistoryOccupancyResponseDTO(
        UUID occupancyId,
        UUID parkingSlotId,
        UUID userId,
        UUID vehicleId,
        String parkingSlotNumber,
        String licensePlate,
        String userName,
        VehicleType vehicleType,
        ParkingSlotType parkingSlotType,
        LocalDateTime startDate,
        LocalDateTime endDate
) {
}
