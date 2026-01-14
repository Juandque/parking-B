package org.park.services;

import lombok.RequiredArgsConstructor;
import org.park.dtos.users.*;
import org.park.dtos.vehicles.VehicleResponseDTO;
import org.park.exceptions.users.DocumentAlreadyRegisteredException;
import org.park.exceptions.users.UserNotFoundException;
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
    private final VehicleService vehicleService;

    public UserResponseDTO createUser(UserRequestDTO userRequestDTO){
        if(!isDocumentAlredyRegistered(userRequestDTO.document())){
            throw new DocumentAlreadyRegisteredException(userRequestDTO.document());
        }
        User user = createUserEntity(userRequestDTO);
        return new UserResponseDTO(user.getId(),user.getName(),user.getEmail(),user.getPhone(),user.getDocument());
    }

    public UserResponseDTO updateUser(UpdateUserRequestDTO updateUserRequestDTO){
        User user = updateUserEntity(updateUserRequestDTO);
        return new UserResponseDTO(user.getId(),user.getName(),user.getEmail(),user.getPhone(),user.getDocument());
    }

    public void deleteUser(UUID id){
        User user = getUserOrThrow(id);
        user.setStatus(Status.INACTIVE);
        userRepository.save(user);
    }

    public UserResponseDTO getUserById(UUID id){
        User user = getUserOrThrow(id);
        return new UserResponseDTO(user.getId(),user.getName(),user.getEmail(),user.getPhone(),user.getDocument());
    }

    public UserProfileResponseDTO  getUserProfile(UUID id){
        User user = getUserOrThrow(id);
        List<VehicleResponseDTO> vehiclesFound=vehicleService.getVehiclesByUserId(id);
        return new UserProfileResponseDTO(user.getId(),user.getName(),user.getDocument(),user.getPhone(),user.getEmail(),vehiclesFound);
    }

    public UserResponseDTO getUserByDocument(String document){
        Optional<User> userOptional = userRepository.findByDocument(document);
        if (userOptional.isEmpty()) {
            throw new UserNotFoundException(document);
        }
        User user = userOptional.get();
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
            throw new UserNotFoundException(id.toString());
        }
        return userOptional.get();
    }

    public User getOrCreateUserByDocument(UserRequestDTO userRequestDTO){
        return userRepository.findByDocument(userRequestDTO.document()).orElseGet(()-> createUserEntity(userRequestDTO));
    }

    private User createUserEntity(UserRequestDTO userRequestDTO){
        User user = new User();
        user.setName(userRequestDTO.name());
        user.setEmail(userRequestDTO.email());
        user.setPhone(userRequestDTO.phone());
        user.setDocument(userRequestDTO.document());
        return userRepository.save(user);
    }

    public User updateUserEntity(UpdateUserRequestDTO updateUserRequestDTO){
        User user = getUserOrThrow(updateUserRequestDTO.id());
        user.setName(updateUserRequestDTO.name());
        user.setEmail(updateUserRequestDTO.email());
        user.setPhone(updateUserRequestDTO.phone());
        return userRepository.save(user);
    }

    public User updateUserByDocument(UserRequestDTO userRequestDTO){
        Optional<User> userOptional = userRepository.findByDocument(userRequestDTO.document());
        if(userOptional.isEmpty()) {
            throw new UserNotFoundException(userRequestDTO.document());
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
