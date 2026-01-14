package org.park.services;

import lombok.RequiredArgsConstructor;
import org.park.dtos.fees.FeeSearchParametersRequestDTO;
import org.park.dtos.occupancies.*;
import org.park.dtos.users.UserRequestDTO;
import org.park.dtos.vehicles.VehicleRequestDTO;
import org.park.exceptions.parkingOccupancies.ParkingOccupancyNotFoundException;
import org.park.model.entities.*;
import org.park.repositories.ParkingOccupancyRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class OccupancyService {
    private ParkingSlotService parkingSlotService;
    private UserService userService;
    private VehicleService vehicleService;
    private VehicleOwnershipService vehicleOwnershipService;
    private FeeService feeService;
    private ParkingOccupancyRepository parkingOccupancyRepository;
    //TODO iniciar ocupacion
    public OccupancyResponseDTO createOccupancy(OccupancyRequestDTO requestDTO) {
        ParkingOccupancy parkingOccupancy = createOccupancyEntity(requestDTO);
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
    //TODO obtener informacion de ocupacion para el pago

    public ParkingOccupancy createOccupancyEntity(OccupancyRequestDTO requestDTO) {
        //Verificar si el puesto esta disponible y obtenerlo
        parkingSlotService.isParkingSlotAvailable(requestDTO.parkingSlotNumber());
        ParkingSlot parkingSlot = parkingSlotService.getParkingSlotByNumber(requestDTO.parkingSlotNumber());
        //Crear el vehicle ownership
        User user = userService.getOrCreateUserByDocument(new UserRequestDTO(requestDTO.userName(),requestDTO.userEmail(),requestDTO.userPhone(),requestDTO.userDocument()));
        Vehicle vehicle = vehicleService.getOrCreateVehicleByLicensePlate(new VehicleRequestDTO(requestDTO.licensePlate(),requestDTO.vehicleType()));
        vehicleOwnershipService.createVehicleOwnership(user, vehicle);
        //Obtener la tarifa
        Fee fee = feeService.getFeeByParameters(new FeeSearchParametersRequestDTO(vehicle.getVehicleType(),parkingSlot.getType(),requestDTO.feeType()));
        //Crear la ocupacion
        ParkingOccupancy parkingOccupancy = new ParkingOccupancy();
        parkingOccupancy.setParkingSlot(parkingSlot);
        //Ocupar el puesto
        parkingSlotService.occupyParkingSlot(parkingSlot.getId());
        parkingOccupancy.setOccupationStartDate(LocalDateTime.now());
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

    public ParkingOccupancy getParkingOccupancyOrThrow(UUID id){
        Optional<ParkingOccupancy> parkingOccupancy = parkingOccupancyRepository.findById(id);
        if(parkingOccupancy.isEmpty()){
            throw new ParkingOccupancyNotFoundException(id.toString());
        }
        return parkingOccupancy.get();
    }

}
