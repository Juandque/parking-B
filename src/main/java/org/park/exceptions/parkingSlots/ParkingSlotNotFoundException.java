package org.park.exceptions.parkingSlots;

public class ParkingSlotNotFoundException extends RuntimeException {
    public ParkingSlotNotFoundException(String term) {
        super("Parking Slot with term " + term + " not found");
    }
}
