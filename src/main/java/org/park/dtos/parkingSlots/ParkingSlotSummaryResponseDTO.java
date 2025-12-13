package org.park.dtos.parkingSlots;

import org.park.model.enums.ParkingSlotStatus;
import org.park.model.enums.ParkingSlotType;

import java.util.UUID;

public record ParkingSlotSummaryResponseDTO(
        UUID id,
        String number,
        ParkingSlotType parkingSlotType,
        ParkingSlotStatus parkingSlotStatus
) {
}
