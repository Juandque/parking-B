package org.park.dtos.vehicles;

import org.park.dtos.users.UserResponseDTO;

public record VehicleProfileResponseDTO(
        VehicleResponseDTO vehicle,
        UserResponseDTO user
) {
}
