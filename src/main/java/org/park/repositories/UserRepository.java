package org.park.repositories;

import org.park.model.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByDocument(String document);
    Optional<User> findUserById(UUID id);
}
