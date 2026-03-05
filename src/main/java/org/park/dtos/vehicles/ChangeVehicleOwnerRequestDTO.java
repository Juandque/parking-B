package org.park.dtos.vehicles;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record ChangeVehicleOwnerRequestDTO(
        @NotNull UUID vehicleId,
        @NotNull @NotBlank String ownerName,
        @NotNull @NotBlank String ownerEmail,
        @NotNull @NotBlank String ownerPhone,
        @NotNull @NotBlank String ownerDocument
) {
}
