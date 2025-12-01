package org.park.model.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.park.model.enums.ParkingSlotStatus;
import org.park.model.enums.ParkingSlotType;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "parking_slots")
public class ParkingSlot {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private UUID id;

    @Column(name = "slot_number")
    private String number;

    @Column(name = "type")
    private ParkingSlotType type;

    @Column(name = "status")
    private ParkingSlotStatus status;

    @OneToMany(mappedBy = "parkingSlot", fetch = FetchType.LAZY)
    private List<ParkingOccupancy> occupancies=new ArrayList<>();
}
