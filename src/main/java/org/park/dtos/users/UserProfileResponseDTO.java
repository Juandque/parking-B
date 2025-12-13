package org.park.dtos.users;

import org.park.dtos.vehicles.VehicleResponseDTO;

import java.util.List;
import java.util.UUID;

public record UserProfileResponseDTO(
        UUID id,
        String name,
        String document,
        String phone,
        String email,
        List<VehicleResponseDTO> vehicles
) {
}
