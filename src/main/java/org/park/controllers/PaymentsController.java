package org.park.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.park.dtos.enums.EnumOptionDTO;
import org.park.dtos.occupancies.OccupancyDetailResponseDTO;
import org.park.dtos.payments.*;
import org.park.model.entities.Payment;
import org.park.services.PaymentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/payments")
@RequiredArgsConstructor
public class PaymentsController {
    private final PaymentService paymentService;

    @PostMapping
    public ResponseEntity<PaymentResponseDTO> createPayment(@Valid @RequestBody CreatePaymentRequestDTO createPaymentRequestDTO) {
        return ResponseEntity.status(HttpStatus.OK).body(paymentService.createPayment(createPaymentRequestDTO));
    }

    @GetMapping
    public ResponseEntity<List<ItemPaymentHistoryResponseDTO>> getAllPayments() {
        return ResponseEntity.status(HttpStatus.OK).body(paymentService.getAllPayments());
    }

    @GetMapping("/statuses")
    public ResponseEntity<List<EnumOptionDTO>> getPaymentStatuses() {
        return ResponseEntity.status(HttpStatus.OK).body(paymentService.getAllPaymentsStatuses());
    }

    @GetMapping("/methods")
    public ResponseEntity<List<EnumOptionDTO>> getPaymentMethods() {
        return ResponseEntity.status(HttpStatus.OK).body(paymentService.getAllPaymentsMethods());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PaymentDetailResponseDTO> getPaymentById(@PathVariable UUID id) {
        return ResponseEntity.status(HttpStatus.OK).body(paymentService.getPaymentById(id));
    }

    @GetMapping("/occupancy/{occupancyId}")
    public ResponseEntity<PaymentDetailResponseDTO> getPaymentByOccupancy(@PathVariable UUID occupancyId) {
        return ResponseEntity.status(HttpStatus.OK).body(paymentService.getPaymentByOccupancyId(occupancyId));
    }

    @PutMapping("/{id}/confirm")
    public ResponseEntity<PaymentConfirmedResponseDTO> confirmPayment(@PathVariable UUID id) {
        return ResponseEntity.status(HttpStatus.OK).body(paymentService.confirmPayment(id));
    }

    @PutMapping("/{id}/cancel")
    public ResponseEntity<PaymentDetailResponseDTO> cancelPayment(@PathVariable UUID id) {
        return ResponseEntity.status(HttpStatus.OK).body(paymentService.cancelPayment(id));
    }
}
