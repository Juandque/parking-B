package org.park.services;

import lombok.RequiredArgsConstructor;
import org.park.dtos.users.*;
import org.park.dtos.vehicles.VehicleResponseDTO;
import org.park.exceptions.alreadyExists.EntityAlreadyExists;
import org.park.exceptions.notFound.EntityNotFound;
import org.park.model.entities.User;
import org.park.model.enums.Status;
import org.park.repositories.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {
    private final UserRepository userRepository;
    private final VehicleOwnershipService vehicleOwnershipService;

    public UserResponseDTO createUser(UserRequestDTO userRequestDTO){
        User user = createUserEntity(userRequestDTO);
        return new UserResponseDTO(user.getId(),user.getName(),user.getEmail(),user.getPhone(),user.getDocument());
    }

    public UserResponseDTO updateUser(UserRequestDTO updateUserRequestDTO, UUID id){
        User user = updateUserEntity(updateUserRequestDTO, id);
        return new UserResponseDTO(user.getId(),user.getName(),user.getEmail(),user.getPhone(),user.getDocument());
    }

    public UserResponseDTO deleteUser(UUID id){
        User user = softDeleteUser(id);
        return new UserResponseDTO(user.getId(),user.getName(),user.getEmail(),user.getPhone(),user.getDocument());
    }

    public UserResponseDTO getUserById(UUID id){
        User user = getUserOrThrow(id);
        return new UserResponseDTO(user.getId(),user.getName(),user.getEmail(),user.getPhone(),user.getDocument());
    }

    public UserProfileResponseDTO  getUserProfile(UUID id){
        User user = getUserOrThrow(id);
        List<VehicleResponseDTO> vehiclesFound= vehicleOwnershipService.getActiveVehiclesByUserId(id).stream().map(v -> new VehicleResponseDTO(v.getId(),v.getLicensePlate(),v.getVehicleType())).toList();
        return new UserProfileResponseDTO(user.getId(),user.getName(),user.getDocument(),user.getPhone(),user.getEmail(),vehiclesFound);
    }

    public UserResponseDTO getUserByDocument(String document){
        User user = getUserEntityByDocument(document);
        return new UserResponseDTO(user.getId(),user.getName(),user.getEmail(),user.getPhone(),user.getDocument());
    }

    public List<UserResponseDTO> getAllUsers(){
        List<User> users = userRepository.findAll();
        List<UserResponseDTO> usersFound = new ArrayList<>();
        for (User user : users) {
            usersFound.add(new UserResponseDTO(user.getId(), user.getName(), user.getEmail(), user.getPhone(), user.getDocument()));
        }
        return usersFound;
    }

    public boolean isDocumentAlredyRegistered(String document){
        return userRepository.findByDocument(document).isPresent();
    }

    public User getUserOrThrow(UUID id){
        Optional<User> userOptional = userRepository.findById(id);
        if(userOptional.isEmpty()){
            throw new EntityNotFound("User with id: "+id+" not found");
        }
        return userOptional.get();
    }

    public User getUserEntityByDocument(String document){
        Optional<User> userOptional = userRepository.findByDocument(document);
        if (userOptional.isEmpty()) {
            throw new EntityNotFound("User with document: "+document+ " already exists");
        }
        return userOptional.get();
    }

    public User getOrCreateUserByDocument(UserRequestDTO userRequestDTO){
        return userRepository.findByDocument(userRequestDTO.document()).orElseGet(()-> createUserEntity(userRequestDTO));
    }

    private User createUserEntity(UserRequestDTO userRequestDTO){
        if(isDocumentAlredyRegistered(userRequestDTO.document())){
            throw new EntityAlreadyExists("User with document: "+userRequestDTO.document()+ " already exists");
        }
        User user = new User();
        user.setName(userRequestDTO.name());
        user.setEmail(userRequestDTO.email());
        user.setPhone(userRequestDTO.phone());
        user.setDocument(userRequestDTO.document());
        user.setStatus(Status.ACTIVE);
        return userRepository.save(user);
    }

    public User updateUserEntity(UserRequestDTO updateUserRequestDTO, UUID id){
        User user = getUserOrThrow(id);
        user.setName(updateUserRequestDTO.name());
        user.setEmail(updateUserRequestDTO.email());
        user.setPhone(updateUserRequestDTO.phone());
        return userRepository.save(user);
    }

    public User softDeleteUser(UUID id){
        User user = getUserOrThrow(id);
        user.setStatus(Status.INACTIVE);
        return userRepository.save(user);
    }

    public User updateUserByDocument(UserRequestDTO userRequestDTO){
        Optional<User> userOptional = userRepository.findByDocument(userRequestDTO.document());
        if(userOptional.isEmpty()) {
            throw new EntityNotFound("User with document: "+userRequestDTO.document()+" not found");
        }
        User user = userOptional.get();
        user.setName(userRequestDTO.name());
        user.setEmail(userRequestDTO.email());
        user.setPhone(userRequestDTO.phone());
        return userRepository.save(user);
    }

    public User createOrUpdateUser(UserRequestDTO userRequestDTO){
        boolean registered = isDocumentAlredyRegistered(userRequestDTO.document());
        User user;
        if(registered){
            user = updateUserByDocument(userRequestDTO);
        }else {
            user = createUserEntity(userRequestDTO);
        }
        return user;
    }
}
