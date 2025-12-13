package org.park.dtos.vehicles;

import org.park.model.enums.VehicleType;

public record CreateVehicleRequestDTO(
        String licensePlate,
        VehicleType vehicleType
) {
}
