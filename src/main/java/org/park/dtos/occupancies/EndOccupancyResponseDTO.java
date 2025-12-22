package org.park.dtos.occupancies;

import java.time.LocalDateTime;
import java.util.UUID;

public record EndOccupancyResponseDTO(
        UUID occupancyId,
        UUID userId,
        UUID vehicleId,
        UUID parkingSlotId,
        LocalDateTime startDate,
        LocalDateTime endDate,
        //TODO cambiar el tipo de objeto
        double totalTime
) {
}
