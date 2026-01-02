package org.park.exceptions.vehicles;

public class LicensePlateAlreadyRegisteredException extends RuntimeException {
    public LicensePlateAlreadyRegisteredException(String licensePlate){super("License Plate" +licensePlate+" already Registered");}
}
