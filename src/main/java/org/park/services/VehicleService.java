package org.park.services;

import lombok.RequiredArgsConstructor;
import org.park.dtos.users.UserRequestDTO;
import org.park.dtos.users.OwnerSummaryDTO;
import org.park.dtos.users.UserResponseDTO;
import org.park.dtos.vehicles.*;
import org.park.exceptions.alreadyExists.EntityAlreadyExists;
import org.park.exceptions.notFound.EntityNotFound;
import org.park.model.entities.User;
import org.park.model.entities.Vehicle;
import org.park.model.entities.VehicleOwnership;
import org.park.model.enums.Status;
import org.park.repositories.VehicleRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class VehicleService {
    private final VehicleOwnershipService vehicleOwnershipService;
    private final VehicleRepository vehicleRepository;
    private final UserService userService;

    public List<VehicleResponseDTO> getVehiclesByUserId(UUID userId){
        List<Vehicle> vehiclesFound = vehicleOwnershipService.getActiveVehiclesByUserId(userId);
        return vehiclesFound.stream().map(v -> new VehicleResponseDTO(v.getId(),v.getLicensePlate(),v.getVehicleType())).toList();
    }

    public List<ItemVehicleHistoryResponseDTO> getAllVehicles(){
        List<VehicleOwnership> ownershipList= vehicleOwnershipService.findOngoingOwnerships();
        return ownershipList.stream().map(
                vo -> new ItemVehicleHistoryResponseDTO(
                        new VehicleResponseDTO(vo.getVehicle().getId(),vo.getVehicle().getLicensePlate(),vo.getVehicle().getVehicleType()),
                        new OwnerSummaryDTO(vo.getUser().getName(),vo.getUser().getPhone(),vo.getUser().getEmail()))).toList();
    }

    public VehicleProfileResponseDTO getVehicleProfile(UUID vehicleId){
        VehicleOwnership ownership= vehicleOwnershipService.getOwnershipByVehicleIdOrThrow(vehicleId);
        User user= ownership.getUser();
        Vehicle vehicle= ownership.getVehicle();
        return new VehicleProfileResponseDTO(
                new VehicleResponseDTO(vehicle.getId(),vehicle.getLicensePlate(),vehicle.getVehicleType()),
                new UserResponseDTO(user.getId(), user.getName(),user.getEmail(),user.getPhone(),user.getDocument()));

    }

    public VehicleResponseDTO createVehicle(VehicleRequestDTO vehicleRequestDTO){
        Vehicle vehicle = createVehicleEntity(vehicleRequestDTO);
        return new VehicleResponseDTO(vehicle.getId(),vehicle.getLicensePlate(),vehicle.getVehicleType());
    }

    public VehicleResponseDTO updateVehicle(UpdateVehicleRequestDTO updateVehicleRequestDTO){
        Vehicle vehicle = updateVehicleEntity(updateVehicleRequestDTO);
        return new VehicleResponseDTO(vehicle.getId(),vehicle.getLicensePlate(),vehicle.getVehicleType());
    }

    public VehicleResponseDTO deleteVehicle(UUID vehicleId){
        Vehicle vehicle = softDeleteVehicle(vehicleId);
        return new  VehicleResponseDTO(vehicle.getId(),vehicle.getLicensePlate(),vehicle.getVehicleType());
    }

    public void changeVehicleOwner(ChangeVehicleOwnerRequestDTO changeVehicleOwnerRequestDTO){
        Vehicle vehicle = getVehicleOrThrow(changeVehicleOwnerRequestDTO.vehicleId());
        VehicleOwnership oldOwnership = vehicleOwnershipService.getOwnershipByVehicleIdOrThrow(changeVehicleOwnerRequestDTO.vehicleId());
        vehicleOwnershipService.endVehicleOwnership(oldOwnership);
        User newOwner=userService.getOrCreateUserByDocument(new UserRequestDTO(
                changeVehicleOwnerRequestDTO.ownerName(),
                changeVehicleOwnerRequestDTO.ownerEmail(),
                changeVehicleOwnerRequestDTO.ownerPhone(),
                changeVehicleOwnerRequestDTO.ownerDocument()));
        vehicleOwnershipService.createVehicleOwnership(newOwner, vehicle);
    }

    public boolean isLicensePlateAlreadyRegistered(String licensePlate){
        return vehicleRepository.findByLicensePlate(licensePlate).isPresent();
    }

    public Vehicle getVehicleOrThrow(UUID vehicleId){
        Optional<Vehicle> optionalVehicle = vehicleRepository.findById(vehicleId);
        if(optionalVehicle.isEmpty()){
            throw new EntityNotFound("Vehicle with id: "+vehicleId+ " not found");
        }
        return optionalVehicle.get();
    }

    public Vehicle createVehicleEntity(VehicleRequestDTO vehicleRequestDTO){
        if (isLicensePlateAlreadyRegistered(vehicleRequestDTO.licensePlate())){
            throw new EntityAlreadyExists("Vehicle with license plate: "+vehicleRequestDTO.licensePlate()+" already exists");
        }
        Vehicle vehicle = new Vehicle();
        vehicle.setLicensePlate(vehicleRequestDTO.licensePlate());
        vehicle.setVehicleType(vehicleRequestDTO.vehicleType());
        vehicle.setStatus(Status.ACTIVE);
        return vehicleRepository.save(vehicle);
    }

    public Vehicle updateVehicleEntity(UpdateVehicleRequestDTO updateVehicleRequestDTO){
        Vehicle vehicle = getVehicleOrThrow(updateVehicleRequestDTO.id());
        vehicle.setLicensePlate(updateVehicleRequestDTO.licensePlate());
        vehicle.setVehicleType(updateVehicleRequestDTO.vehicleType());
        return vehicleRepository.save(vehicle);
    }

    public Vehicle softDeleteVehicle(UUID vehicleId){
        Vehicle vehicle = getVehicleOrThrow(vehicleId);
        vehicle.setStatus(Status.INACTIVE);
        return vehicleRepository.save(vehicle);
    }

    public Vehicle getOrCreateVehicleByLicensePlate(VehicleRequestDTO vehicleRequestDTO){
        return vehicleRepository.findByLicensePlate(vehicleRequestDTO.licensePlate()).orElseGet(() -> createVehicleEntity(vehicleRequestDTO));
    }

    public Vehicle updateVehicleByLicensePlate(VehicleRequestDTO vehicleRequestDTO){
        Optional<Vehicle> vehicleOptional = vehicleRepository.findByLicensePlate(vehicleRequestDTO.licensePlate());
        if(vehicleOptional.isEmpty()){
            throw new EntityNotFound("Vehicle with license plate: "+vehicleRequestDTO.licensePlate()+" not found");
        }
        Vehicle vehicle = vehicleOptional.get();
        vehicle.setLicensePlate(vehicleRequestDTO.licensePlate());
        vehicle.setVehicleType(vehicleRequestDTO.vehicleType());
        return vehicleRepository.save(vehicle);
    }

    public Vehicle createOrUpdateVehicle(VehicleRequestDTO vehicleRequestDTO){
        boolean registered =  isLicensePlateAlreadyRegistered(vehicleRequestDTO.licensePlate());
        Vehicle vehicle;
        if(registered){
            vehicle=updateVehicleByLicensePlate(vehicleRequestDTO);
        }else {
            vehicle = createVehicleEntity(vehicleRequestDTO);
        }
        return vehicle;
    }
}
