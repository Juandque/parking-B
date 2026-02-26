package org.park.exceptions.differentStatusExpected;

import org.park.exceptions.BaseBusinessException;
import org.springframework.http.HttpStatus;

public class ParkingSlotOccupiedException extends BaseBusinessException {
    public ParkingSlotOccupiedException(String number) {
        super("Parking Slot " + number + " is not available", "ENTITY-STATUS-CONFLICT", HttpStatus.CONFLICT);
    }
}
