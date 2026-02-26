package org.park.services;

import lombok.RequiredArgsConstructor;
import org.park.model.entities.Fee;
import org.park.model.entities.ParkingOccupancy;
import org.park.model.enums.FeeType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Duration;

@Service
@RequiredArgsConstructor
@Transactional
public class PaymentCalculatorService {

    public BigDecimal calculateTotal(ParkingOccupancy parkingOccupancy) {
        Fee fee = parkingOccupancy.getAplicableFee();
        return fee.getFeeType().equals(FeeType.FRACTION) ? calculateHourly(parkingOccupancy,fee) : fee.getPrice();
    }

    private BigDecimal calculateHourly(ParkingOccupancy parkingOccupancy, Fee fee) {
        Duration duration = Duration.between(parkingOccupancy.getOccupationStartDate(), parkingOccupancy.getOccupationEndDate());
        long minutes = duration.toMinutes();
        long hours = (long) Math.ceil(minutes/60.0);
        return fee.getPrice().multiply(BigDecimal.valueOf(hours));

    }
}
