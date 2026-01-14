package org.park.dtos.vehicles;

import org.park.model.enums.VehicleType;

public record VehicleRequestDTO(
        String licensePlate,
        VehicleType vehicleType
) {
}
