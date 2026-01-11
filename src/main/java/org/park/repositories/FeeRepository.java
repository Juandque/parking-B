package org.park.repositories;

import org.park.model.entities.Fee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface FeeRepository extends JpaRepository<Fee, UUID> {
}
