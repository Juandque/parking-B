package org.park.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.park.dtos.occupancies.*;
import org.park.services.OccupancyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/occupancies")
@RequiredArgsConstructor
public class ParkingOccupanciesController {
    private final OccupancyService  occupancyService;

    @PostMapping("/start")
    public ResponseEntity<OccupancyResponseDTO> startOccupancy(@Valid @RequestBody OccupancyRequestDTO occupancyRequestDTO) {
        return ResponseEntity.status(HttpStatus.OK).body(occupancyService.createOccupancy(occupancyRequestDTO));
    }

    @PutMapping("/{id}/update")
    public ResponseEntity<OccupancyResponseDTO> updateOccupancy(@PathVariable UUID id,@Valid @RequestBody OccupancyRequestDTO occupancyRequestDTO) {
        return ResponseEntity.status(HttpStatus.OK).body(occupancyService.updateOccupancy(id, occupancyRequestDTO));
    }

    @GetMapping("/{id}/info-update")
    public ResponseEntity<OccupancyInfoUpdateResponseDTO> getOccupancyBeforeUpdate(@PathVariable UUID id){
        return ResponseEntity.status(HttpStatus.OK).body(occupancyService.getOccupancyBeforeUpdate(id));
    }

    @GetMapping("/active")
    public ResponseEntity<List<ItemActiveOccupanciesResponseDTO>> getActiveOccupancies(){
        return ResponseEntity.status(HttpStatus.OK).body(occupancyService.getActiveOccupancies());
    }

    @GetMapping
    public ResponseEntity<List<ItemHistoryOccupancyResponseDTO>> getAllOccupancies(){
        return ResponseEntity.status(HttpStatus.OK).body(occupancyService.getAllOccupancies());
    }

    @GetMapping("/{id}")
    public ResponseEntity<OccupancyDetailResponseDTO> getOccupancy(@Valid @PathVariable UUID id){
        return ResponseEntity.status(HttpStatus.OK).body(occupancyService.getOccupancyDetail(id));
    }

    @PutMapping("/{id}/end")
    public ResponseEntity<EndOccupancyResponseDTO> endOccupancy(@Valid @PathVariable UUID id){
        return ResponseEntity.status(HttpStatus.OK).body(occupancyService.endOccupancy(id));
    }

    @GetMapping("/{id}/info-payment")
    public ResponseEntity<OccupancyInfoPaymentResponseDTO> getOccupancyInfoForPayment(@PathVariable UUID id){
        return ResponseEntity.status(HttpStatus.OK).body(occupancyService.getOccupancyInfoPayment(id));
    }
}
