package org.park.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.park.dtos.users.UserRequestDTO;
import org.park.dtos.users.UserProfileResponseDTO;
import org.park.dtos.users.UserResponseDTO;
import org.park.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UsersController {

    private final UserService userService;
    @PostMapping
    public ResponseEntity<UserResponseDTO> addUser(@Valid @RequestBody UserRequestDTO userRequestDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.createUser(userRequestDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable UUID id){
        return ResponseEntity.status(HttpStatus.OK).body(userService.deleteUser(id));
    }

    @PutMapping("/{id}")
    public  ResponseEntity<UserResponseDTO> updateUser(@Valid @RequestBody UserRequestDTO updateUserRequestDTO, @PathVariable UUID id){
        return ResponseEntity.status(HttpStatus.OK).body(userService.updateUser(updateUserRequestDTO, id));
    }

    @GetMapping("/{id}")
    public  ResponseEntity<UserResponseDTO> getUserById(@PathVariable UUID id){
        return ResponseEntity.status(HttpStatus.OK).body(userService.getUserById(id));
    }

    @GetMapping("/{id}/profile")
    public  ResponseEntity<UserProfileResponseDTO> getUserProfile(@PathVariable UUID id){
        return ResponseEntity.status(HttpStatus.OK).body(userService.getUserProfile(id));
    }

    @GetMapping("/{document}/document")
    public ResponseEntity<UserResponseDTO> getUserByDocument(@PathVariable String document){
        return ResponseEntity.status(HttpStatus.OK).body(userService.getUserByDocument(document));
    }

    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> getAllUsers(){
        return ResponseEntity.status(HttpStatus.OK).body(userService.getAllUsers());
    }

}
