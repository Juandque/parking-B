package org.park.model.entities;

import jakarta.persistence.*;
import lombok.*;
import org.park.model.enums.FeeType;
import org.park.model.enums.ParkingSlotType;
import org.park.model.enums.VehicleType;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "fees")
public class Fee {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private UUID id;

    @Column(name = "vehicle_types")
    @Enumerated(EnumType.STRING)
    private VehicleType vehicleType;

    @Column(name = "parking_slot_types")
    @Enumerated(EnumType.STRING)
    private ParkingSlotType parkingSlotType;

    @Column(name = "fee_types")
    @Enumerated(EnumType.STRING)
    private FeeType feeType;

    @Column(name = "prices")
    private double price;

    @Column(name = "validity_start_dates")
    private LocalDateTime validityStartDate;

    @Column(name = "validity_end_dates")
    private LocalDateTime validityEndDate;

    @OneToMany(mappedBy = "aplicableFee", fetch = FetchType.LAZY)
    private List<ParkingOccupancy> occupancies = new ArrayList<>();
}
