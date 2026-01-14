package org.park.exceptions.parkingOccupancies;

public class ParkingOccupancyNotFoundException extends RuntimeException {
    public ParkingOccupancyNotFoundException(String term) {
        super("Parking Occupancy with term "+term+" Not Found");
    }
}
