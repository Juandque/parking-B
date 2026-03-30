package org.park.dtos.fees;

import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import org.park.model.enums.FeeType;
import org.park.model.enums.ParkingSlotType;
import org.park.model.enums.VehicleType;

public record FeeSearchParametersRequestDTO(
        @NotNull @Enumerated VehicleType vehicleType,
        @NotNull @Enumerated ParkingSlotType parkingSlotType,
        @NotNull @Enumerated FeeType feeType
) {
}
