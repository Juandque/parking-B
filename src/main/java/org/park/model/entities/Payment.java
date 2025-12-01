package org.park.model.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.park.model.enums.PaymentMethod;
import org.park.model.enums.PaymentStatus;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "payments")
public class Payment {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private UUID id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "occupancy_id", nullable = false)
    private ParkingOccupancy parkingOccupancy;

    @Column(name = "total_amounts")
    private double totalAmount;

    @Column(name = "payment_dates")
    private LocalDateTime paymentDate;

    @Column(name = "payment_methods")
    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;

    @Column(name = "payment_status")
    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;
}
