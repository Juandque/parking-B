package org.park.controllers;

import lombok.RequiredArgsConstructor;
import org.park.dtos.occupancies.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/occupancies")
@RequiredArgsConstructor
public class ParkingOccupanciesController {

    @PostMapping("/start")
    public ResponseEntity<OccupancyResponseDTO> startOccupancy(@RequestBody OccupancyRequestDTO occupancyRequestDTO) {
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    @PutMapping("/{id}/update")
    public ResponseEntity<OccupancyResponseDTO> updateOccupancy(@PathVariable UUID id, @RequestBody OccupancyRequestDTO occupancyRequestDTO) {
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    @GetMapping("/{id}/info-update")
    public ResponseEntity<OccupancyInfoUpdateResponseDTO> getOccupancyBeforeUpdate(@PathVariable UUID id){
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    @GetMapping("/active")
    public ResponseEntity<List<ItemActiveOccupanciesResponseDTO>> getActiveOccupancies(){
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    @GetMapping
    public ResponseEntity<List<ItemHistoryOccupancyResponseDTO>> getAllOccupancies(){
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OccupancyDetailResponseDTO> getOccupancy(@PathVariable UUID id){
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    @PutMapping("/{id}/end")
    public ResponseEntity<EndOccupancyResponseDTO> endOccupancy(@PathVariable UUID id){
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    @GetMapping("/{id}/for-payment")
    public ResponseEntity<OccupancyInfoPaymentResponseDTO> getOccupancyInfoForPayment(@PathVariable UUID id){
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }
}
