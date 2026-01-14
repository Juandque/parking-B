package org.park.controllers;

import lombok.RequiredArgsConstructor;
import org.park.dtos.enums.EnumOptionDTO;
import org.park.dtos.vehicles.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/vehicles")
@RequiredArgsConstructor
public class VehiclesController {
    @GetMapping
    public ResponseEntity<List<ItemVehicleHistoryResponseDTO>> getAllVehicles(){
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    @GetMapping("/types")
    public ResponseEntity<List<EnumOptionDTO>> getVehicleTypes(){
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    @GetMapping("/{id}")
    public ResponseEntity<VehicleProfileResponseDTO> getVehicleById(@PathVariable UUID id){
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    @PostMapping
    public ResponseEntity<VehicleResponseDTO> createVehicle(@RequestBody VehicleRequestDTO vehicle){
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    @PutMapping("/{id}")
    public ResponseEntity<VehicleResponseDTO> updateVehicle(@RequestBody UpdateVehicleRequestDTO vehicle){
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteVehicle(@PathVariable UUID id){
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    @PutMapping("/change-owner")
    public ResponseEntity<?> changeVehicleOwner(@RequestBody ChangeVehicleOwnerRequestDTO changeVehicleOwnerRequestDTO){
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }
}
