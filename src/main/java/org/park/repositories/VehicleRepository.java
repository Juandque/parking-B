package org.park.repositories;

import org.park.model.entities.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, UUID> {
    //Optional<Vehicle> getByVehicleId(UUID id);

    Optional<Vehicle> findByLicensePlate(String licensePlate);
}
