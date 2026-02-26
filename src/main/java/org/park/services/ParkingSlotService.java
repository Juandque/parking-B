package org.park.services;

import lombok.RequiredArgsConstructor;
import org.park.dtos.enums.EnumOptionDTO;
import org.park.dtos.parkingSlots.ParkingSlotRequestDTO;
import org.park.dtos.parkingSlots.ParkingSlotSummaryResponseDTO;
import org.park.exceptions.notFound.EntityNotFound;
import org.park.exceptions.differentStatusExpected.ParkingSlotOccupiedException;
import org.park.model.entities.ParkingSlot;
import org.park.model.enums.ParkingSlotStatus;
import org.park.model.enums.ParkingSlotType;
import org.park.repositories.ParkingSlotRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class ParkingSlotService {
    private final ParkingSlotRepository parkingSlotRepository;

    //TODO obtener todos los puestos
    public List<ParkingSlotSummaryResponseDTO> getAllParkingSlots(){
        List<ParkingSlot> parkingSlots = parkingSlotRepository.findAll();
        return parkingSlots.stream().map(ps -> new ParkingSlotSummaryResponseDTO(ps.getId(),ps.getNumber(),ps.getType(),ps.getStatus())).toList();
    }

    //TODO obtener tipos de puestos
    public List<EnumOptionDTO> getParkingSlotTypes(){
        return Arrays.stream(ParkingSlotType.values()).map(v -> new EnumOptionDTO(v.name(),formatLabel(v.name()))).toList();
    }

    //TODO obtener tipos de estados de los puestos
    public List<EnumOptionDTO> getParkingSlotStatuses(){
        return Arrays.stream(ParkingSlotStatus.values()).map(v -> new EnumOptionDTO(v.name(),formatLabel(v.name()))).toList();
    }

    //TODO obtener puesto por id
    public ParkingSlotSummaryResponseDTO getParkingSlotById(UUID id){
        ParkingSlot parkingSlot = getParkingSlotOrThrow(id);
        return new ParkingSlotSummaryResponseDTO(parkingSlot.getId(),parkingSlot.getNumber(),parkingSlot.getType(),parkingSlot.getStatus());
    }

    //TODO crear puesto
    public ParkingSlotSummaryResponseDTO createParkingSlot(ParkingSlotRequestDTO parkingSlotRequestDTO){
        ParkingSlot parkingSlot = createParkingSlotEntity(parkingSlotRequestDTO);
        return new ParkingSlotSummaryResponseDTO(parkingSlot.getId(),parkingSlot.getNumber(),parkingSlot.getType(),parkingSlot.getStatus());
    }

    //TODO actualizar puesto
    public ParkingSlotSummaryResponseDTO updateParkingSlot(UUID id, ParkingSlotRequestDTO parkingSlotRequestDTO){
        ParkingSlot parkingSlot = updateParkingSlotEntity(id, parkingSlotRequestDTO);
        return new ParkingSlotSummaryResponseDTO(parkingSlot.getId(),parkingSlot.getNumber(),parkingSlot.getType(),parkingSlot.getStatus());
    }

    //TODO bloquear puesto
    public ParkingSlotSummaryResponseDTO blockParkingSlot(UUID id){
        ParkingSlot parkingSlot = blockParkingSlotEntity(id);
        return new ParkingSlotSummaryResponseDTO(parkingSlot.getId(),parkingSlot.getNumber(),parkingSlot.getType(),parkingSlot.getStatus());
    }

    //TODO desbloquear puesto
    public ParkingSlotSummaryResponseDTO unblockParkingSlot(UUID id){
        ParkingSlot parkingSlot = unblockParkingSlotEntity(id);
        return new ParkingSlotSummaryResponseDTO(parkingSlot.getId(),parkingSlot.getNumber(),parkingSlot.getType(),parkingSlot.getStatus());
    }

    //TODO eliminar puesto
    public void deleteParkingSlot(UUID id){
        ParkingSlot parkingSlot = getParkingSlotOrThrow(id);
        parkingSlotRepository.delete(parkingSlot);
    }
    //TODO obtener el estado genereal de los puestos

    public ParkingSlot getParkingSlotOrThrow(UUID id){
        Optional<ParkingSlot> parkingSlot = parkingSlotRepository.findById(id);
        if(parkingSlot.isEmpty()){
            throw new EntityNotFound(id.toString());
        }
        return parkingSlot.get();
    }

    public void checkParkingSlotNumberAvailable(String number){
        Optional<ParkingSlot> parkingSlotOptional = parkingSlotRepository.findByNumber(number);
        if(parkingSlotOptional.isPresent()){
            throw new ParkingSlotOccupiedException(number);
        }
    }

    public ParkingSlot createParkingSlotEntity(ParkingSlotRequestDTO  parkingSlotRequestDTO){
        checkParkingSlotNumberAvailable(parkingSlotRequestDTO.number());
        ParkingSlot parkingSlot = new ParkingSlot();
        parkingSlot.setNumber(parkingSlotRequestDTO.number());
        parkingSlot.setType(parkingSlotRequestDTO.type());
        parkingSlot.setStatus(ParkingSlotStatus.UNOCCUPIED);
        return parkingSlotRepository.save(parkingSlot);
    }

    public ParkingSlot updateParkingSlotEntity(UUID id,ParkingSlotRequestDTO parkingSlotRequestDTO){
        checkParkingSlotNumberAvailable(parkingSlotRequestDTO.number());
        ParkingSlot parkingSlot = getParkingSlotOrThrow(id);
        parkingSlot.setNumber(parkingSlotRequestDTO.number());
        parkingSlot.setType(parkingSlotRequestDTO.type());
        return parkingSlotRepository.save(parkingSlot);
    }

    public ParkingSlot blockParkingSlotEntity(UUID id){
        ParkingSlot parkingSlot = getParkingSlotOrThrow(id);
        parkingSlot.setStatus(ParkingSlotStatus.OUT_OF_SERVICE);
        return parkingSlotRepository.save(parkingSlot);
    }

    public ParkingSlot unblockParkingSlotEntity(UUID id){
        ParkingSlot parkingSlot = getParkingSlotOrThrow(id);
        parkingSlot.setStatus(ParkingSlotStatus.UNOCCUPIED);
        return parkingSlotRepository.save(parkingSlot);
    }

    public void isParkingSlotAvailable(String number){
        Optional<ParkingSlot> parkingSlotOptional = parkingSlotRepository.findByNumber(number);
        if(parkingSlotOptional.isEmpty()){
            throw new EntityNotFound("Parking Slot with number: "+number+" not found");
        }
        ParkingSlot parkingSlot = parkingSlotOptional.get();
        if(parkingSlot.getStatus() == ParkingSlotStatus.OUT_OF_SERVICE ||  parkingSlot.getStatus() == ParkingSlotStatus.OCCUPIED){
            throw new ParkingSlotOccupiedException(number);
        }
    }

    public ParkingSlot getParkingSlotByNumber(String number){
        Optional<ParkingSlot> parkingSlotOptional = parkingSlotRepository.findByNumber(number);
        if(parkingSlotOptional.isEmpty()){
            throw new EntityNotFound("Parking Slot with number: "+number+" not found");
        }
        return parkingSlotOptional.get();
    }

    public void occupyParkingSlot(UUID id){
        ParkingSlot parkingSlot = getParkingSlotOrThrow(id);
        parkingSlot.setStatus(ParkingSlotStatus.OCCUPIED);
        parkingSlotRepository.save(parkingSlot);
    }

    public void unoccupyParkingSlot(UUID id){
        ParkingSlot parkingSlot = getParkingSlotOrThrow(id);
        parkingSlot.setStatus(ParkingSlotStatus.UNOCCUPIED);
        parkingSlotRepository.save(parkingSlot);
    }

    private String formatLabel(String value) {
        return value.replace("_", " ").toLowerCase();
    }
}
