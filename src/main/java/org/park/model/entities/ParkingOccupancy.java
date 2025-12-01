package org.park.model.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.park.model.enums.FeeType;

import java.time.LocalDateTime;
import java.util.UUID;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "parking_occupations")
public class ParkingOccupancy {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vehicle_id", nullable = false)
    private Vehicle vehicle;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parking_slot_id", nullable = false)
    private ParkingSlot parkingSlot;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "aplicable_fee_id", nullable = false)
    private Fee aplicableFee;

    @Column(name = "fee_types")
    @Enumerated(EnumType.STRING)
    private FeeType feeType;

    @Column(name = "occupation_start_dates")
    private LocalDateTime occupationStartDate;

    @Column(name = "occupation_end_dates")
    private LocalDateTime occupationEndDate;

    @OneToOne(mappedBy = "parkingOccupancy", cascade = CascadeType.ALL)
    private Payment payment;
}
