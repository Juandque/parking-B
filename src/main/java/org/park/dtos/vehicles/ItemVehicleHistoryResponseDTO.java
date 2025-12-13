package org.park.dtos.vehicles;

import org.park.dtos.users.OwnerSummaryDTO;

public record ItemVehicleHistoryResponseDTO(
        VehicleResponseDTO vehicleInfo,
        OwnerSummaryDTO ownerInfo

) {
}
