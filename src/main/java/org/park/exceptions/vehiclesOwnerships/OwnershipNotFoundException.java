package org.park.exceptions.vehiclesOwnerships;

public class OwnershipNotFoundException extends RuntimeException {
    public OwnershipNotFoundException(String term) {
        super("Ownersip with term: " + term + " not found");
    }
}
