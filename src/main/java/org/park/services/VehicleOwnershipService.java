package org.park.services;

import lombok.RequiredArgsConstructor;
import org.park.exceptions.notFound.EntityNotFound;
import org.park.model.entities.User;
import org.park.model.entities.Vehicle;
import org.park.model.entities.VehicleOwnership;
import org.park.repositories.VehicleOwnershipRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class VehicleOwnershipService {
    private final VehicleOwnershipRepository vehicleOwnershipRepository;

    public List<Vehicle> getActiveVehiclesByUserId(UUID id){
        return vehicleOwnershipRepository.findByUserIdAndEndDateIsNull(id);
    }

    public List<VehicleOwnership> findOngoingOwnerships(){
        return vehicleOwnershipRepository.findByEndDateIsNull();
    }

    public VehicleOwnership getOwnershipByVehicleIdOrThrow(UUID id){
        Optional<VehicleOwnership> ownershipOptional = vehicleOwnershipRepository.findByVehicleIdAndEndDateIsNull(id);
        if(ownershipOptional.isEmpty()){
            throw new EntityNotFound("Vehicle ownership with id: "+id+" not found");
        }
        return ownershipOptional.get();
    }

    public void endVehicleOwnership(VehicleOwnership vehicleOwnership){
        vehicleOwnership.setEndDate(LocalDateTime.now());
        vehicleOwnershipRepository.save(vehicleOwnership);
    }

    public void createVehicleOwnership(User user, Vehicle  vehicle){
        VehicleOwnership newVehicleOwnership = new VehicleOwnership();
        newVehicleOwnership.setUser(user);
        newVehicleOwnership.setVehicle(vehicle);
        newVehicleOwnership.setStartDate(LocalDateTime.now());
        vehicleOwnershipRepository.save(newVehicleOwnership);
    }

    public void findOrCreateVehicleOwnership(User user, Vehicle vehicle){
        if(vehicleOwnershipRepository.findByVehicleIdAndUserId(vehicle.getId(), user.getId()).isEmpty()){
            createVehicleOwnership(user,vehicle);
        }
    }
}
