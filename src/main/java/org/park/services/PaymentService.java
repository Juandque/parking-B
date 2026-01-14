package org.park.services;

import lombok.RequiredArgsConstructor;
import org.park.dtos.payments.CreatePaymentRequestDTO;
import org.park.dtos.payments.PaymentResponseDTO;
import org.park.model.entities.Payment;
import org.park.model.enums.PaymentStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Transactional
@RequiredArgsConstructor
public class PaymentService {
    //TODO crear pago
    public PaymentResponseDTO createPayment(CreatePaymentRequestDTO createPaymentRequestDTO) {

    }
    //TODO obtener todos los pagos
    //TODO obtener los estados posibles de un pago
    //TODO obtener metodos de pago
    //TODO obtener un pago por id
    //TODO obtener un pago por ocupacion
    //TODO confirmar pago

    public Payment createPaymentEntity(CreatePaymentRequestDTO createPaymentRequestDTO) {
        Payment payment = new Payment();
        payment.setPaymentMethod(createPaymentRequestDTO.paymentMethod());
        payment.setPaymentStatus(PaymentStatus.PENDING);
        payment.setParkingOccupancy();
    }
}
