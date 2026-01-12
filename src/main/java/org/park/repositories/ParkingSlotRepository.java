package org.park.repositories;

import org.park.model.entities.ParkingSlot;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ParkingSlotRepository extends JpaRepository<ParkingSlot, UUID> {
    Optional<ParkingSlot> findByNumber(String number);
}
