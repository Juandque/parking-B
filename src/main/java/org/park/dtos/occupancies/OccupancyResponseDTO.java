package org.park.dtos.occupancies;

import java.time.LocalDateTime;
import java.util.UUID;

public record OccupancyResponseDTO(
        UUID occupancyId,
        UUID userId,
        UUID vehicleId,
        UUID parkingSlotId,
        LocalDateTime startDate
) {
}
