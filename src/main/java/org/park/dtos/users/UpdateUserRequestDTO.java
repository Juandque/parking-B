package org.park.dtos.users;

import java.util.UUID;

public record UpdateUserRequestDTO(
        UUID id,
        String name,
        String email,
        String phone) {

}
