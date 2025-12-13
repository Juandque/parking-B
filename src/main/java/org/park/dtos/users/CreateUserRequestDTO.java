package org.park.dtos.users;

public record CreateUserRequestDTO (
        String name,
        String email,
        String phone,
        String document){

}
