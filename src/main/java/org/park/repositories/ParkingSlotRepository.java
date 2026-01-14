package org.park.repositories;

import org.park.model.entities.ParkingSlot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ParkingSlotRepository extends JpaRepository<ParkingSlot, UUID> {
    Optional<ParkingSlot> findByNumber(String number);
}
