package org.park.exceptions.vehicles;

public class VehicleNotFoundException extends RuntimeException {
    public VehicleNotFoundException(String term) {
        super("Vehicles with term search: " + term +"Not found");
    }
}
