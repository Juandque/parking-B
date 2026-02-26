package org.park.dtos.fees;

import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import org.park.model.enums.FeeType;
import org.park.model.enums.ParkingSlotType;
import org.park.model.enums.VehicleType;

import java.math.BigDecimal;

public record FeeRequestDTO(
        @NotBlank @Enumerated VehicleType vehicleType,
        @NotBlank @Enumerated ParkingSlotType parkingSlotType,
        @NotBlank @Enumerated FeeType feeType,
        @NotBlank @Positive BigDecimal price
) {
}
