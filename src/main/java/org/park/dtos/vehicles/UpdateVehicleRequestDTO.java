package org.park.dtos.vehicles;

import org.park.model.enums.VehicleType;

import java.util.UUID;

public record UpdateVehicleRequestDTO(
        UUID id,
        String licensePlate,
        VehicleType vehicleType
) {
}
