package org.park.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.park.dtos.enums.EnumOptionDTO;
import org.park.dtos.fees.FeeRequestDTO;
import org.park.dtos.fees.FeeResponseDTO;
import org.park.dtos.fees.FeeStatusResponseDTO;
import org.park.dtos.fees.ItemFeeDetailResponseDTO;
import org.park.model.entities.Fee;
import org.park.services.FeeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/fees")
@RequiredArgsConstructor
public class FeesController {

    private final FeeService feeService;

    @GetMapping
    public ResponseEntity<List<ItemFeeDetailResponseDTO>> getFees(){
        return ResponseEntity.status(HttpStatus.OK).body(feeService.getAllFees());
    }

    @GetMapping("/types")
    public ResponseEntity<List<EnumOptionDTO>> getFeeTypes(){
        return ResponseEntity.status(HttpStatus.OK).body(feeService.getFeeTypes());
    }

    @GetMapping("/{id}")
    public ResponseEntity<FeeResponseDTO> getFeeById(@PathVariable UUID id){
        return ResponseEntity.status(HttpStatus.OK).body(feeService.getFeeById(id));
    }

    @PostMapping
    public ResponseEntity<FeeResponseDTO> createFee(@Valid @RequestBody FeeRequestDTO feeRequestDTO) {
        FeeResponseDTO responseDTO = feeService.createFee(feeRequestDTO);
        return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<FeeResponseDTO> updateFee(@PathVariable UUID id, @Valid @RequestBody FeeRequestDTO feeRequestDTO) {
        FeeResponseDTO responseDTO = feeService.updateFee(id, feeRequestDTO);
        return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
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
