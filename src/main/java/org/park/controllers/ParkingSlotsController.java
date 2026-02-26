package org.park.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.park.dtos.enums.EnumOptionDTO;
import org.park.dtos.parkingSlots.ParkingSlotRequestDTO;
import org.park.dtos.parkingSlots.ParkingSlotSummaryResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/parking-slots")
@RequiredArgsConstructor
public class ParkingSlotsController {

    @GetMapping
    public ResponseEntity<List<ParkingSlotSummaryResponseDTO>> getAllParkingSlots(){
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    @GetMapping("/types")
    public ResponseEntity<List<EnumOptionDTO>> getParkingSlotsTypes(){
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    @GetMapping("/statuses")
    public ResponseEntity<List<EnumOptionDTO>> getParkingSlotsStatuses(){
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ParkingSlotSummaryResponseDTO> getParkingSlotById(@PathVariable UUID id){
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    @PostMapping
    public ResponseEntity<ParkingSlotSummaryResponseDTO> createParkingSlot(@Valid @RequestBody ParkingSlotRequestDTO parkingSlotRequestDTO){
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ParkingSlotSummaryResponseDTO> updateParkingSlot(@Valid @RequestBody ParkingSlotRequestDTO parkingSlotRequestDTO, @PathVariable UUID id){
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    @PutMapping("/{id}/block")
    public ResponseEntity<ParkingSlotSummaryResponseDTO> blockParkingSlotById(@PathVariable UUID id){
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    @PutMapping("/{id}/unblock")
    public ResponseEntity<ParkingSlotSummaryResponseDTO> unblockParkingSlotById(@PathVariable UUID id){
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteParkingSlotById(@PathVariable UUID id){
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    @GetMapping("/general-status")
    public ResponseEntity<?> getParkingSlotsGeneralStatus(){
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }
}
