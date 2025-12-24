package org.park.dtos.fees;

import org.park.model.enums.Status;

import java.util.UUID;

public record FeeStatusResponseDTO(
        UUID id,
        Status status
) {
}
