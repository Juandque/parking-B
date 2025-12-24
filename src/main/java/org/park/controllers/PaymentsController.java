package org.park.controllers;

import lombok.RequiredArgsConstructor;
import org.park.dtos.enums.EnumOptionDTO;
import org.park.dtos.occupancies.OccupancyDetailResponseDTO;
import org.park.dtos.payments.CreatePaymentRequestDTO;
import org.park.dtos.payments.ItemPaymentHistoryResponseDTO;
import org.park.dtos.payments.PaymentDetailResponseDTO;
import org.park.model.entities.Payment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/payments")
@RequiredArgsConstructor
public class PaymentsController {

    @PostMapping
    public ResponseEntity<?> createPayment(@RequestBody CreatePaymentRequestDTO createPaymentRequestDTO) {
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    @GetMapping
    public ResponseEntity<List<ItemPaymentHistoryResponseDTO>> getAllPayments() {
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    @GetMapping("/statuses")
    public ResponseEntity<List<EnumOptionDTO>> getPaymentStatuses() {
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    @GetMapping("/methods")
    public ResponseEntity<List<EnumOptionDTO>> getPaymentMethods() {
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PaymentDetailResponseDTO> getPaymentById(@PathVariable UUID id) {
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    @GetMapping("/occupancy/{occupancyId}")
    public ResponseEntity<PaymentDetailResponseDTO> getPaymentByOccupancy(@PathVariable UUID occupancyId) {
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    @PutMapping("/{id}/confirm")
    public ResponseEntity<?> confirmPayment(@PathVariable UUID id) {
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }
}
