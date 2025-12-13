package org.park.dtos.vehicles;

import java.util.UUID;

public record ChangeVehicleOwnerRequestDTO(
        UUID vehicleId,
        String ownerName,
        String ownerEmail,
        String ownerPhone,
        String ownerDocument
) {
}
