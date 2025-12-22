package org.park.dtos.occupancies;

import org.park.model.enums.ParkingSlotType;
import org.park.model.enums.VehicleType;

import java.util.UUID;

public record ItemActiveOccupanciesResponseDTO(
        UUID occupancyId,
        String parkingSlotNumber,
        String licensePlate,
        String userName,
        VehicleType vehicleType,
        ParkingSlotType parkingSlotType,
        String userPhone
) {
}
