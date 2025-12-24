package org.park.dtos.fees;

import org.park.model.enums.FeeType;
import org.park.model.enums.ParkingSlotType;
import org.park.model.enums.VehicleType;

public record FeeRequestDTO(
        VehicleType vehicleType,
        ParkingSlotType parkingSlotType,
        FeeType feeType,
        double price
) {
}
