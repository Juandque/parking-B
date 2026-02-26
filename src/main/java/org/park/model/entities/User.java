package org.park.model.entities;

import jakarta.persistence.*;
import lombok.*;
import org.park.model.enums.Status;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "ownershipHistory")
@Entity
@Table(name = "users")
public class User {
    @Id @Column(name = "id", nullable = false, updatable = false) @GeneratedValue
    private UUID id;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "phone")
    private String phone;

    @Column(name = "document", unique = true)
    private String document;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private Status status;

    @OneToMany(mappedBy = "user", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    private List<VehicleOwnership> ownershipHistory = new ArrayList<>();

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<ParkingOccupancy> occupancies = new ArrayList<>();
}
