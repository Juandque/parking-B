package org.park.dtos.occupancies;

import org.park.model.enums.FeeType;
import org.park.model.enums.VehicleType;

public record OccupancyRequestDTO(
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
