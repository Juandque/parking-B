package org.park.services;

import lombok.RequiredArgsConstructor;
import org.park.dtos.users.CreateUserRequestDTO;
import org.park.dtos.users.OwnerSummaryDTO;
import org.park.dtos.users.UserResponseDTO;
import org.park.dtos.vehicles.*;
import org.park.exceptions.users.UserNotFoundException;
import org.park.exceptions.vehicles.LicensePlateAlreadyRegisteredException;
import org.park.exceptions.vehicles.VehicleNotFoundException;
import org.park.model.entities.User;
import org.park.model.entities.Vehicle;
import org.park.model.entities.VehicleOwnership;
import org.park.model.enums.Status;
import org.park.repositories.VehicleRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class VehicleService {
    private VehicleOwnershipService vehicleOwnershipService;
    private VehicleRepository vehicleRepository;
    private UserService userService;

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

    public VehicleResponseDTO createVehicle(CreateVehicleRequestDTO  createVehicleRequestDTO){
        if (!isLicensePlateAlreadyRegistered(createVehicleRequestDTO.licensePlate())){
            throw new LicensePlateAlreadyRegisteredException(createVehicleRequestDTO.licensePlate());
        }
        Vehicle vehicle = new Vehicle();
        vehicle.setLicensePlate(createVehicleRequestDTO.licensePlate());
        vehicle.setVehicleType(createVehicleRequestDTO.vehicleType());
        vehicleRepository.save(vehicle);
        return new VehicleResponseDTO(vehicle.getId(),vehicle.getLicensePlate(),vehicle.getVehicleType());
    }

    public VehicleResponseDTO updateVehicle(UpdateVehicleRequestDTO updateVehicleRequestDTO){
        Vehicle vehicle = getVehicleOrThrow(updateVehicleRequestDTO.id());
        vehicle.setLicensePlate(updateVehicleRequestDTO.licensePlate());
        vehicle.setVehicleType(updateVehicleRequestDTO.vehicleType());
        vehicleRepository.save(vehicle);
        return new VehicleResponseDTO(vehicle.getId(),vehicle.getLicensePlate(),vehicle.getVehicleType());
    }

    public void deleteVehicle(UUID vehicleId){
        Vehicle vehicle = getVehicleOrThrow(vehicleId);
        vehicle.setStatus(Status.INACTIVE);
        vehicleRepository.save(vehicle);
    }

    public void changeVehicleOwner(ChangeVehicleOwnerRequestDTO changeVehicleOwnerRequestDTO){
        Vehicle vehicle = getVehicleOrThrow(changeVehicleOwnerRequestDTO.vehicleId());
        VehicleOwnership oldOwnership = vehicleOwnershipService.getOwnershipByVehicleIdOrThrow(changeVehicleOwnerRequestDTO.vehicleId());
        vehicleOwnershipService.endVehicleOwnership(oldOwnership);
        User newOwner=userService.getOrCreateUserByDocument(new CreateUserRequestDTO(
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
            throw new VehicleNotFoundException(vehicleId.toString());
        }
        return optionalVehicle.get();
    }
}
