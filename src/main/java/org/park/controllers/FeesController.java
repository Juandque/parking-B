package org.park.controllers;

import lombok.RequiredArgsConstructor;
import org.park.dtos.enums.EnumOptionDTO;
import org.park.dtos.fees.FeeRequestDTO;
import org.park.dtos.fees.FeeResponseDTO;
import org.park.dtos.fees.FeeStatusResponseDTO;
import org.park.dtos.fees.ItemFeeDetailResponseDTO;
import org.park.model.entities.Fee;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/fees")
@RequiredArgsConstructor
public class FeesController {

    @GetMapping
    public ResponseEntity<List<ItemFeeDetailResponseDTO>> getFees(){
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    @GetMapping("/types")
    public ResponseEntity<List<EnumOptionDTO>> getFeeTypes(){
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FeeResponseDTO> getFeeById(@PathVariable UUID id){
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    @PostMapping
    public ResponseEntity<FeeResponseDTO> createFee(@RequestBody FeeRequestDTO feeRequestDTO) {
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    @PutMapping("/{id}")
    public ResponseEntity<FeeResponseDTO> updateFee(@PathVariable UUID id, @RequestBody FeeRequestDTO feeRequestDTO) {
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    @PutMapping("/{id}/activate")
    public ResponseEntity<FeeStatusResponseDTO> activateFee(@PathVariable UUID id) {
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    @PutMapping("/{id}/deactivate")
    public ResponseEntity<FeeStatusResponseDTO> deactivateFee(@PathVariable UUID id) {
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }
}
