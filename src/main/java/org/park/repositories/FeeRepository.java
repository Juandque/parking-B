package org.park.repositories;

import org.park.dtos.fees.FeeSearchParametersRequestDTO;
import org.park.model.entities.Fee;
import org.park.model.enums.FeeType;
import org.park.model.enums.ParkingSlotType;
import org.park.model.enums.VehicleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface FeeRepository extends JpaRepository<Fee, UUID> {
    @Query("""
select f from Fee f where f.feeType =: feeType and f.parkingSlotType =: parkingSlotType and f.vehicleType =: vehicleType and f.validityEndDate is null
""")
    Optional<Fee> getFeeByParameters(VehicleType vehicleType, ParkingSlotType parkingSlotType, FeeType feeType);
}
