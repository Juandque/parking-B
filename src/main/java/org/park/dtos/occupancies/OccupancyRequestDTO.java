package org.park.dtos.occupancies;

import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.park.model.enums.FeeType;
import org.park.model.enums.VehicleType;

public record OccupancyRequestDTO(
        @NotNull @NotBlank String userName,
        @NotNull @NotBlank String userDocument,
        @NotNull @NotBlank String userPhone,
        @NotNull @NotBlank String userEmail,
        @NotNull @NotBlank String licensePlate,
        @NotNull @Enumerated VehicleType vehicleType,
        @NotNull @Enumerated FeeType feeType,
        @NotNull @NotBlank String parkingSlotNumber
) {
}
