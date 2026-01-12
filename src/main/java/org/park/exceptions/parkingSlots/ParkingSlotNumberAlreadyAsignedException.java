package org.park.exceptions.parkingSlots;

public class ParkingSlotNumberAlreadyAsignedException extends RuntimeException {
    public ParkingSlotNumberAlreadyAsignedException(String number) {
        super("Parking Slot number " + number + " already asigned");
    }
}
