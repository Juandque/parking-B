package org.park.model.entities;

import jakarta.persistence.*;

import lombok.*;
import org.park.model.enums.Status;
import org.park.model.enums.VehicleType;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "ownershipHistory")
@Entity
@Table(name = "vehicles")
public class Vehicle {
    @Id
    @Column(name = "id", nullable = false, updatable = false)
    @GeneratedValue
    private UUID id;

    @Column(name = "license_plate", nullable = false, unique = true)
    private String licensePlate;

    @Column(name = "vehicle_type")
    @Enumerated(EnumType.STRING)
    private VehicleType vehicleType;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private Status status;

    @OneToMany(mappedBy = "vehicle", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    private List<VehicleOwnership> ownershipHistory=new ArrayList<>();

    @OneToMany(mappedBy = "vehicle", fetch = FetchType.LAZY)
    private List<ParkingOccupancy> occupancies=new ArrayList<>();
}
