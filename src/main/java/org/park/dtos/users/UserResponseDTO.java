package org.park.dtos.users;

import java.util.UUID;

public record UserResponseDTO(
        UUID id,
        String name,
        String email,
        String phone,
        String document) {

}
