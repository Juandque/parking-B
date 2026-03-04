package org.park.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.park.dtos.enums.EnumOptionDTO;
import org.park.dtos.parkingSlots.ParkingSlotRequestDTO;
import org.park.dtos.parkingSlots.ParkingSlotSummaryResponseDTO;
import org.park.services.ParkingSlotService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/parking-slots")
@RequiredArgsConstructor
public class ParkingSlotsController {
    private final ParkingSlotService parkingSlotService;

    @GetMapping
    public ResponseEntity<List<ParkingSlotSummaryResponseDTO>> getAllParkingSlots(){
        return ResponseEntity.status(HttpStatus.OK).body(parkingSlotService.getAllParkingSlots());
    }

    @GetMapping("/types")
    public ResponseEntity<List<EnumOptionDTO>> getParkingSlotsTypes(){
        return ResponseEntity.status(HttpStatus.OK).body(parkingSlotService.getParkingSlotTypes());
    }

    @GetMapping("/statuses")
    public ResponseEntity<List<EnumOptionDTO>> getParkingSlotsStatuses(){
        return ResponseEntity.status(HttpStatus.OK).body(parkingSlotService.getParkingSlotStatuses());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ParkingSlotSummaryResponseDTO> getParkingSlotById(@PathVariable UUID id){
        return ResponseEntity.status(HttpStatus.OK).body(parkingSlotService.getParkingSlotById(id));
    }

    @PostMapping
    public ResponseEntity<ParkingSlotSummaryResponseDTO> createParkingSlot(@Valid @RequestBody ParkingSlotRequestDTO parkingSlotRequestDTO){
        return ResponseEntity.status(HttpStatus.OK).body(parkingSlotService.createParkingSlot(parkingSlotRequestDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ParkingSlotSummaryResponseDTO> updateParkingSlot(@Valid @RequestBody ParkingSlotRequestDTO parkingSlotRequestDTO, @PathVariable UUID id){
        return ResponseEntity.status(HttpStatus.OK).body(parkingSlotService.updateParkingSlot(id, parkingSlotRequestDTO));
    }

    @PutMapping("/{id}/block")
    public ResponseEntity<ParkingSlotSummaryResponseDTO> blockParkingSlotById(@PathVariable UUID id){
        return ResponseEntity.status(HttpStatus.OK).body(parkingSlotService.blockParkingSlot(id));
    }

    @PutMapping("/{id}/unblock")
    public ResponseEntity<ParkingSlotSummaryResponseDTO> unblockParkingSlotById(@PathVariable UUID id){
        return ResponseEntity.status(HttpStatus.OK).body(parkingSlotService.unblockParkingSlot(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ParkingSlotSummaryResponseDTO> deleteParkingSlotById(@PathVariable UUID id){
        return ResponseEntity.status(HttpStatus.OK).body(parkingSlotService.deleteParkingSlot(id));
    }

    @GetMapping("/general-status")
    public ResponseEntity<?> getParkingSlotsGeneralStatus(){
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }
}
