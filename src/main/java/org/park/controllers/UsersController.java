package org.park.controllers;

import lombok.RequiredArgsConstructor;
import org.park.dtos.users.UserRequestDTO;
import org.park.dtos.users.UpdateUserRequestDTO;
import org.park.dtos.users.UserProfileResponseDTO;
import org.park.dtos.users.UserResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UsersController {
    @PostMapping
    public ResponseEntity<UserResponseDTO> addUser(@RequestBody UserRequestDTO userRequestDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(null);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable UUID id){
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    @PutMapping
    public  ResponseEntity<UserResponseDTO> updateUser(@RequestBody UpdateUserRequestDTO updateUserRequestDTO){
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    @GetMapping("/{id}")
    public  ResponseEntity<UserResponseDTO> getUserById(@PathVariable UUID id){
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    @GetMapping("/{id}/profile")
    public  ResponseEntity<UserProfileResponseDTO> getUserProfile(@PathVariable UUID id){
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    @GetMapping("/by-document/{document}")
    public ResponseEntity<UserResponseDTO> getUserByDocument(@PathVariable String document){
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    public ResponseEntity<List<UserResponseDTO>> getAllUsers(){
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

}
