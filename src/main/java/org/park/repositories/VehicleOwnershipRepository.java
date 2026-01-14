package org.park.repositories;

import org.park.model.entities.User;
import org.park.model.entities.Vehicle;
import org.park.model.entities.VehicleOwnership;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface VehicleOwnershipRepository extends JpaRepository<VehicleOwnership, UUID> {
    List<VehicleOwnership> findByUserId(UUID id);
    @Query("""
select vo.vehicle from VehicleOwnership vo where vo.user.id = :id and vo.endDate is null
""")
    List<Vehicle> findByUserIdAndEndDateIsNull(UUID id);
    Optional<VehicleOwnership> findByVehicleIdAndEndDateIsNull(UUID vehicleId);
    @Query("""
select vo from VehicleOwnership vo where vo.endDate is null
""")
    List<VehicleOwnership> findByEndDateIsNull();
    Optional<VehicleOwnership> findByVehicleIdAndUserId(UUID vehicleId, UUID userId);

}
