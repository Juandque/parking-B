package org.park.services;

import lombok.RequiredArgsConstructor;
import org.park.dtos.enums.EnumOptionDTO;
import org.park.dtos.payments.*;
import org.park.exceptions.alreadyExists.EntityAlreadyExists;
import org.park.exceptions.differentStatusExpected.ParkingOccupancyStillOngoingException;
import org.park.exceptions.differentStatusExpected.PaymentStatusConflictException;
import org.park.exceptions.notFound.EntityNotFound;
import org.park.model.entities.ParkingOccupancy;
import org.park.model.entities.Payment;
import org.park.model.enums.FeeType;
import org.park.model.enums.PaymentMethod;
import org.park.model.enums.PaymentStatus;
import org.park.repositories.PaymentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class PaymentService {
    private final OccupancyService occupancyService;
    private final PaymentRepository paymentRepository;
    private final PaymentCalculatorService paymentCalculatorService;
    //TODO crear pago
    public PaymentResponseDTO createPayment(CreatePaymentRequestDTO createPaymentRequestDTO) {
        Payment payment = createPaymentEntity(createPaymentRequestDTO);
        return new PaymentResponseDTO(payment.getId(),payment.getTotalAmount(),payment.getPaymentMethod(),payment.getPaymentStatus());
    }
    //TODO obtener todos los pagos
    public List<ItemPaymentHistoryResponseDTO> getAllPayments(){
        List<Payment> payments = paymentRepository.findAll();
        return payments.stream().map(p -> new ItemPaymentHistoryResponseDTO(p.getId(),p.getParkingOccupancy().getUser().getName(),p.getParkingOccupancy().getVehicle().getLicensePlate(), p.getPaymentMethod(), p.getPaymentStatus(),p.getTotalAmount())).toList();
    }
    //TODO obtener los estados posibles de un pago
    public List<EnumOptionDTO> getAllPaymentsStatuses(){
        return Arrays.stream(PaymentStatus.values()).map(ps -> new EnumOptionDTO(ps.name(),formatLabel(ps.name()))).toList();
    }
    //TODO obtener metodos de pago
    public List<EnumOptionDTO> getAllPaymentsOptions(){
        return Arrays.stream(PaymentMethod.values()).map(pm -> new EnumOptionDTO(pm.name(),formatLabel(pm.name()))).toList();
    }
    //TODO obtener un pago por id
    public PaymentDetailResponseDTO getPaymentById(UUID id) {
        Payment payment = getPaymentOrThrow(id);
        return new PaymentDetailResponseDTO(payment.getId(),payment.getParkingOccupancy().getUser().getName(), payment.getParkingOccupancy().getVehicle().getLicensePlate(),payment.getPaymentMethod(),payment.getPaymentStatus(),payment.getTotalAmount());
    }
    //TODO obtener un pago por ocupacion
    public PaymentDetailResponseDTO getPaymentByOccupancyId(UUID occupancyId) {
        Payment payment = getPaymentEntityByOccupancyId(occupancyId);
        return new PaymentDetailResponseDTO(payment.getId(),payment.getParkingOccupancy().getUser().getName(), payment.getParkingOccupancy().getVehicle().getLicensePlate(),payment.getPaymentMethod(),payment.getPaymentStatus(),payment.getTotalAmount());
    }
    //TODO confirmar pago
    public PaymentDetailResponseDTO confirmPayment(UUID id) {
        Payment payment = confirmPaymentEntity(id);
        return new PaymentDetailResponseDTO(payment.getId(),payment.getParkingOccupancy().getUser().getName(), payment.getParkingOccupancy().getVehicle().getLicensePlate(),payment.getPaymentMethod(),payment.getPaymentStatus(),payment.getTotalAmount());
    }
    //TODO cancelar pago
    public PaymentDetailResponseDTO cancelPayment(UUID id) {
        Payment payment = cancelPaymentEntity(id);
        return new PaymentDetailResponseDTO(payment.getId(),payment.getParkingOccupancy().getUser().getName(), payment.getParkingOccupancy().getVehicle().getLicensePlate(),payment.getPaymentMethod(),payment.getPaymentStatus(),payment.getTotalAmount());
    }

    public Payment createPaymentEntity(CreatePaymentRequestDTO createPaymentRequestDTO) {
        Payment payment = new Payment();
        ParkingOccupancy parkingOccupancy = occupancyService.getParkingOccupancyOrThrow(createPaymentRequestDTO.occupancyId());
        if(parkingOccupancy.getOccupationEndDate() == null && parkingOccupancy.getFeeType() == FeeType.FRACTION) {
            throw new ParkingOccupancyStillOngoingException("Parking occupancy of type FRACTION with id "+parkingOccupancy.getId()+" has no End Date, can´t create a payment");
        }
        if(parkingOccupancy.getPayment() != null){
            throw new EntityAlreadyExists("Parking Occupancy with id: " + parkingOccupancy.getPayment().getId() + " already has a Payment");
        }
        BigDecimal totalAmount = paymentCalculatorService.calculateTotal(parkingOccupancy);
        payment.setPaymentMethod(createPaymentRequestDTO.paymentMethod());
        payment.setPaymentStatus(PaymentStatus.PENDING);
        payment.setParkingOccupancy(parkingOccupancy);
        payment.setTotalAmount(totalAmount);
        return paymentRepository.save(payment);
    }

    public Payment getPaymentOrThrow(UUID id) {
        Optional<Payment> payment = paymentRepository.findById(id);
        if(payment.isEmpty()){
            throw new EntityNotFound("Payment with id "+id+" not found");
        }
        return payment.get();
    }

    public Payment getPaymentEntityByOccupancyId(UUID occupancyId) {
        ParkingOccupancy parkingOccupancy = occupancyService.getParkingOccupancyOrThrow(occupancyId);
        return getPaymentOrThrow(parkingOccupancy.getPayment().getId());
    }

    public Payment confirmPaymentEntity(UUID id) {
        Payment payment = isPaymentPending(id);
        payment.setPaymentDate(LocalDateTime.now());
        payment.setPaymentStatus(PaymentStatus.PAID);
        return paymentRepository.save(payment);
    }

    public Payment cancelPaymentEntity(UUID id) {
        Payment payment = isPaymentPending(id);
        payment.setPaymentStatus(PaymentStatus.CANCELLED);
        return paymentRepository.save(payment);
    }

    public Payment isPaymentPending(UUID id) {
        Payment payment = getPaymentOrThrow(id);
        if(!payment.getPaymentStatus().equals(PaymentStatus.PENDING)){
            throw new PaymentStatusConflictException("Payment with id "+id+" is not pending");
        }
        return payment;
    }

    private String formatLabel(String value) {
        return value.replace("_", " ").toLowerCase();
    }
}
