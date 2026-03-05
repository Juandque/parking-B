package org.park.exceptions.differentStatusExpected;

import org.park.exceptions.BaseBusinessException;
import org.springframework.http.HttpStatus;

public class ParkingOccupancyAlreadyEnded extends BaseBusinessException {
    public ParkingOccupancyAlreadyEnded(String mensaje) {
        super(mensaje, "ENTITY-STATUS-CONFLICT", HttpStatus.CONFLICT);
    }
}
