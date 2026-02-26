package org.park.exceptions.differentStatusExpected;

import org.park.exceptions.BaseBusinessException;
import org.springframework.http.HttpStatus;

public class ParkingOccupancyStillOngoingException extends BaseBusinessException {
    public ParkingOccupancyStillOngoingException(String message) {
        super(message, "ENTITY-STATUS-CONFLICT", HttpStatus.CONFLICT);
    }
}
