package org.park.dtos.parkingSlots;

import org.park.model.enums.ParkingSlotType;

public record ParkingSlotRequestDTO(
        String number,
        ParkingSlotType type
) {
}
