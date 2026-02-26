package org.park.dtos.users;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public record UserRequestDTO(
        @NotBlank(message = "User name is required") String name,
        @Email(message = "email requires a proper mail format") String email,
        @Positive(message = "phone requires a valid number format") String phone,
        @NotBlank(message = "document is required") String document){

}
