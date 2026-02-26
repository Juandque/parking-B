package org.park.services;

import lombok.RequiredArgsConstructor;
import org.park.dtos.fees.FeeSearchParametersRequestDTO;
import org.park.dtos.occupancies.*;
import org.park.dtos.users.UserRequestDTO;
import org.park.dtos.vehicles.VehicleRequestDTO;
import org.park.exceptions.notFound.EntityNotFound;
import org.park.model.entities.*;
import org.park.model.enums.FeeType;
import org.park.repositories.ParkingOccupancyRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class OccupancyService {
    private final ParkingSlotService parkingSlotService;
    private final UserService userService;
    private final VehicleService vehicleService;
    private final VehicleOwnershipService vehicleOwnershipService;
    private final FeeService feeService;
    private final ParkingOccupancyRepository parkingOccupancyRepository;

    //TODO iniciar ocupacion
    public OccupancyResponseDTO createOccupancy(OccupancyRequestDTO requestDTO) {
        LocalDateTime endDate = null;
        if(requestDTO.feeType().equals(FeeType.MONTHLY)){
            endDate = LocalDateTime.now().plusMonths(1);
        }
        ParkingOccupancy parkingOccupancy = createOccupancyEntity(requestDTO, endDate);
        return new  OccupancyResponseDTO(parkingOccupancy.getId(),parkingOccupancy.getVehicle().getId(), parkingOccupancy.getUser().getId(),parkingOccupancy.getParkingSlot().getId(),parkingOccupancy.getOccupationStartDate());
    }
    //TODO actualizar ocupacion
    public OccupancyResponseDTO updateOccupancy(UUID id, OccupancyRequestDTO requestDTO) {
        ParkingOccupancy parkingOccupancy = updateOccupancyEntity(id, requestDTO);
        return new OccupancyResponseDTO(parkingOccupancy.getId(),parkingOccupancy.getVehicle().getId(), parkingOccupancy.getUser().getId(),parkingOccupancy.getParkingSlot().getId(),parkingOccupancy.getOccupationStartDate());
    }
    //TODO obtener ocupacion antes de actualizar
    public OccupancyInfoUpdateResponseDTO  getOccupancyBeforeUpdate(UUID id) {
        ParkingOccupancy parkingOccupancy = getParkingOccupancyOrThrow(id);
        return new OccupancyInfoUpdateResponseDTO(parkingOccupancy.getId(),
                parkingOccupancy.getParkingSlot().getId(), parkingOccupancy.getUser().getId(), parkingOccupancy.getVehicle().getId(),parkingOccupancy.getAplicableFee().getId(),
                parkingOccupancy.getUser().getName(), parkingOccupancy.getUser().getDocument(), parkingOccupancy.getUser().getPhone(), parkingOccupancy.getUser().getEmail(),
                parkingOccupancy.getVehicle().getLicensePlate(), parkingOccupancy.getVehicle().getVehicleType(),parkingOccupancy.getFeeType(),parkingOccupancy.getParkingSlot().getNumber());
    }
    //TODO obtener ocupaciones activas
    public List<ItemActiveOccupanciesResponseDTO> getActiveOccupancies(UUID id) {
        List<ParkingOccupancy> occupanciesFound = parkingOccupancyRepository.findByOccupationEndDateNull();
        return occupanciesFound.stream().map(
                po -> new ItemActiveOccupanciesResponseDTO(
                        po.getId(),po.getParkingSlot().getNumber(),po.getVehicle().getLicensePlate(),
                        po.getUser().getName(),po.getVehicle().getVehicleType(),po.getParkingSlot().getType(),
                        po.getUser().getPhone())).toList();
    }
    //TODO obtener todas las ocupaciones
    public List<ItemHistoryOccupancyResponseDTO> getAllOccupancies() {
        List<ParkingOccupancy> occupanciesFound = parkingOccupancyRepository.findAll();
        return occupanciesFound.stream()
                .map(po -> new ItemHistoryOccupancyResponseDTO(
                        po.getId(),po.getParkingSlot().getId(),po.getUser().getId(),po.getVehicle().getId(),
                        po.getParkingSlot().getNumber(),po.getVehicle().getLicensePlate(),po.getUser().getName(),
                        po.getVehicle().getVehicleType(), po.getParkingSlot().getType(),po.getOccupationStartDate(),
                        po.getOccupationEndDate())).toList();
    }
    //TODO obtener una ocupacion
    public OccupancyDetailResponseDTO getOccupancyDetail(UUID id) {
        ParkingOccupancy po = getParkingOccupancyOrThrow(id);
        return new OccupancyDetailResponseDTO(
                po.getId(),po.getParkingSlot().getId(),po.getUser().getId(),po.getVehicle().getId(),po.getAplicableFee().getId(),po.getPayment().getId(),
                po.getUser().getName(),po.getUser().getDocument(),po.getUser().getPhone(),po.getUser().getEmail(),
                po.getVehicle().getLicensePlate(),po.getVehicle().getVehicleType(),
                po.getFeeType(),po.getParkingSlot().getNumber(),po.getOccupationStartDate(),po.getOccupationEndDate(),
                po.getAplicableFee().getPrice(),po.getPayment().getPaymentMethod(),
                po.getPayment().getPaymentDate(),po.getPayment().getPaymentStatus(),po.getPayment().getTotalAmount());
    }
    //TODO terminar ocupacion
    public EndOccupancyResponseDTO endOccupancy(UUID id) {
        ParkingOccupancy po = endParkingOccupancyEntity(id);
        double totalTime = calculateTotalTime(po);
        return new EndOccupancyResponseDTO(po.getId(),po.getUser().getId(),po.getVehicle().getId(),po.getParkingSlot().getId(),po.getOccupationStartDate(),po.getOccupationEndDate(),totalTime);
    }

    //TODO obtener informacion de ocupacion para el pago
    public OccupancyInfoPaymentResponseDTO getOccupancyInfoPayment(UUID id) {
        ParkingOccupancy po = getParkingOccupancyOrThrow(id);
        return new OccupancyInfoPaymentResponseDTO(po.getId(),po.getParkingSlot().getId(),po.getUser().getId(),po.getVehicle().getId(),po.getAplicableFee().getId(),po.getPayment().getId(),po.getUser().getName(),po.getUser().getDocument(),po.getUser().getPhone(),po.getUser().getEmail(),po.getVehicle().getLicensePlate(),po.getVehicle().getVehicleType(),po.getFeeType(),po.getParkingSlot().getNumber(),po.getOccupationStartDate(),po.getOccupationEndDate(),po.getAplicableFee().getPrice(),po.getPayment().getTotalAmount());
    }

    public ParkingOccupancy createOccupancyEntity(OccupancyRequestDTO requestDTO, LocalDateTime endDate) {
        //Verificar si el puesto esta disponible y obtenerlo
        parkingSlotService.isParkingSlotAvailable(requestDTO.parkingSlotNumber());
        ParkingSlot parkingSlot = parkingSlotService.getParkingSlotByNumber(requestDTO.parkingSlotNumber());
        //Crear el vehicle ownership
        User user = userService.getOrCreateUserByDocument(new UserRequestDTO(requestDTO.userName(),requestDTO.userEmail(),requestDTO.userPhone(),requestDTO.userDocument()));
        Vehicle vehicle = vehicleService.getOrCreateVehicleByLicensePlate(new VehicleRequestDTO(requestDTO.licensePlate(),requestDTO.vehicleType()));
        vehicleOwnershipService.findOrCreateVehicleOwnership(user, vehicle);
        //Obtener la tarifa
        Fee fee = feeService.getFeeByParameters(new FeeSearchParametersRequestDTO(vehicle.getVehicleType(),parkingSlot.getType(),requestDTO.feeType()));
        //Crear la ocupacion
        ParkingOccupancy parkingOccupancy = new ParkingOccupancy();
        parkingOccupancy.setParkingSlot(parkingSlot);
        //Ocupar el puesto
        parkingSlotService.occupyParkingSlot(parkingSlot.getId());
        parkingOccupancy.setOccupationStartDate(LocalDateTime.now());
        parkingOccupancy.setOccupationEndDate(endDate);
        parkingOccupancy.setUser(user);
        parkingOccupancy.setVehicle(vehicle);
        parkingOccupancy.setAplicableFee(fee);
        parkingOccupancy.setFeeType(requestDTO.feeType());
        return parkingOccupancyRepository.save(parkingOccupancy);
    }

    public ParkingOccupancy updateOccupancyEntity(UUID id,OccupancyRequestDTO requestDTO) {
        ParkingOccupancy parkingOccupancy = getParkingOccupancyOrThrow(id);
        //Verificar si el puesto esta disponible
        parkingSlotService.isParkingSlotAvailable(requestDTO.parkingSlotNumber());
        //Desocupar puesto
        parkingSlotService.unoccupyParkingSlot(parkingOccupancy.getParkingSlot().getId());
        //Obtener el nuevo puesto
        ParkingSlot parkingSlot = parkingSlotService.getParkingSlotByNumber(requestDTO.parkingSlotNumber());
        parkingOccupancy.setParkingSlot(parkingSlot);
        //Ocupar el puesto
        parkingSlotService.occupyParkingSlot(parkingSlot.getId());
        //Actualizar o crear usuario, vehiculo y ownership
        User user = userService.createOrUpdateUser(new UserRequestDTO(requestDTO.userName(),requestDTO.userEmail(),requestDTO.userPhone(),requestDTO.userDocument()));
        Vehicle vehicle = vehicleService.createOrUpdateVehicle(new VehicleRequestDTO(requestDTO.licensePlate(),requestDTO.vehicleType()));
        vehicleOwnershipService.findOrCreateVehicleOwnership(user, vehicle);
        parkingOccupancy.setParkingSlot(parkingSlot);
        parkingOccupancy.setVehicle(vehicle);
        parkingOccupancy.setUser(user);
        parkingOccupancy.setFeeType(requestDTO.feeType());
        return parkingOccupancyRepository.save(parkingOccupancy);
    }

    public ParkingOccupancy endParkingOccupancyEntity(UUID id) {
        ParkingOccupancy parkingOccupancy = getParkingOccupancyOrThrow(id);
        parkingOccupancy.setOccupationEndDate(LocalDateTime.now());
        parkingSlotService.unoccupyParkingSlot(parkingOccupancy.getParkingSlot().getId());
        return parkingOccupancyRepository.save(parkingOccupancy);
    }

    public ParkingOccupancy getParkingOccupancyOrThrow(UUID id){
        Optional<ParkingOccupancy> parkingOccupancy = parkingOccupancyRepository.findById(id);
        if(parkingOccupancy.isEmpty()){
            throw new EntityNotFound("Parking Occupancy with id: "+id+ " not found");
        }
        return parkingOccupancy.get();
    }

    public double calculateTotalTime(ParkingOccupancy parkingOccupancy){
        Duration duration = Duration.between(parkingOccupancy.getOccupationStartDate(),parkingOccupancy.getOccupationEndDate());
        return duration.toMinutes();
    }

}
