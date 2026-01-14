package org.park.exceptions.parkingSlots;

public class ParkingSlotOccupiedException extends RuntimeException {
    public ParkingSlotOccupiedException(String number) {
        super("Parking Slot with number: " + number + " is already occupied");
    }
}
