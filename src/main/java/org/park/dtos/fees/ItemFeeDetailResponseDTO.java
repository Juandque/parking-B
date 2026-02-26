package org.park.dtos.fees;

import org.park.model.enums.FeeType;
import org.park.model.enums.ParkingSlotType;
import org.park.model.enums.VehicleType;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record ItemFeeDetailResponseDTO(
        UUID id,
        VehicleType vehicleType,
        ParkingSlotType parkingSlotType,
        FeeType feeType,
        BigDecimal price,
        LocalDateTime validityStartDate,
        LocalDateTime validityEndDate
) {
}
