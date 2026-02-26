package org.park.services;

import lombok.RequiredArgsConstructor;
import org.park.dtos.enums.EnumOptionDTO;
import org.park.dtos.fees.FeeRequestDTO;
import org.park.dtos.fees.FeeResponseDTO;
import org.park.dtos.fees.FeeSearchParametersRequestDTO;
import org.park.dtos.fees.ItemFeeDetailResponseDTO;
import org.park.exceptions.notFound.EntityNotFound;
import org.park.model.entities.Fee;
import org.park.model.enums.FeeType;
import org.park.repositories.FeeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
@Transactional
public class FeeService {
    private final FeeRepository feeRepository;

    //TODO obtener tarifas
    public List<ItemFeeDetailResponseDTO> getAllFees(){
        List<Fee> feeList = feeRepository.findAll();
        return feeList.stream().map(fee -> new ItemFeeDetailResponseDTO(
                fee.getId(),fee.getVehicleType(),fee.getParkingSlotType(),
                fee.getFeeType(),fee.getPrice(),fee.getValidityStartDate(),fee.getValidityEndDate()))
                .toList();
    }

    //TODO obtener tipos de tarifas
    public List<EnumOptionDTO> getFeeTypes(){
        return Arrays.stream(FeeType.values()).map(v -> new EnumOptionDTO(v.name(),formatLabel(v.name()))).toList();
    }

    //TODO obtener tarifa por id
    public FeeResponseDTO getFeeById(UUID id) {
        Fee fee = getFeeOrThrow(id);
        return new FeeResponseDTO(fee.getId(),fee.getVehicleType(),fee.getParkingSlotType(),fee.getFeeType(),fee.getPrice());
    }

    //TODO crear tarifa
    public FeeResponseDTO createFee(FeeRequestDTO feeRequestDTO) {
        Fee feeEntity = createFeeEntity(feeRequestDTO);
        return new FeeResponseDTO(feeEntity.getId(),feeEntity.getVehicleType(),feeEntity.getParkingSlotType(),feeEntity.getFeeType(),feeEntity.getPrice());
    }

    //TODO actualizar tarifa
    public FeeResponseDTO updateFee(UUID id,FeeRequestDTO feeRequestDTO) {
        Fee fee = UpdateFeeEntity(id,feeRequestDTO);
        return new FeeResponseDTO(fee.getId(),fee.getVehicleType(),fee.getParkingSlotType(),fee.getFeeType(),fee.getPrice());
    }

    //TODO activar tarifa
    public void activateFee(UUID id) {
        Fee fee = getFeeOrThrow(id);
        fee.setValidityEndDate(null);
        feeRepository.save(fee);
    }
    //TODO desactivar tarifa
    public void deactivateFee(UUID id) {
        Fee fee = getFeeOrThrow(id);
        fee.setValidityEndDate(LocalDateTime.now());
        feeRepository.save(fee);
    }

    public Fee getFeeOrThrow(UUID id) {
        Optional<Fee> optionalFee = feeRepository.findById(id);
        if(optionalFee.isEmpty()){
            throw new EntityNotFound("Fee with id: "+id+ " not found");
        }
        return optionalFee.get();
    }

    public Fee createFeeEntity(FeeRequestDTO feeRequestDTO) {
        Fee fee = new Fee();
        fee.setFeeType(feeRequestDTO.feeType());
        fee.setParkingSlotType(feeRequestDTO.parkingSlotType());
        fee.setPrice(feeRequestDTO.price());
        fee.setFeeType(feeRequestDTO.feeType());
        fee.setValidityStartDate(LocalDateTime.now());
        return feeRepository.save(fee);
    }

    public Fee UpdateFeeEntity(UUID id, FeeRequestDTO feeRequestDTO) {
        Fee feeEntity = getFeeOrThrow(id);
        feeEntity.setPrice(feeRequestDTO.price());
        feeEntity.setParkingSlotType(feeRequestDTO.parkingSlotType());
        feeEntity.setFeeType(feeRequestDTO.feeType());
        feeEntity.setVehicleType(feeRequestDTO.vehicleType());
        return feeRepository.save(feeEntity);
    }

    public Fee getFeeByParameters(FeeSearchParametersRequestDTO requestDTO) {
        Optional<Fee> optionalFee = feeRepository.getFeeByParameters(requestDTO.vehicleType(),requestDTO.parkingSlotType(),requestDTO.feeType());
        if (optionalFee.isEmpty()){
            throw new EntityNotFound("Fee with search parameters: "+requestDTO.vehicleType().toString()+" - "+requestDTO.parkingSlotType().toString()+" - "+requestDTO.feeType().toString()+ " not found");
        }
        return optionalFee.get();
    }

    private String formatLabel(String value) {
        return value.replace("_", " ").toLowerCase();
    }
}
