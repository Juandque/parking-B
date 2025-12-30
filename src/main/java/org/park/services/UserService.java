package org.park.services;

import lombok.RequiredArgsConstructor;
import org.park.dtos.users.*;
import org.park.exceptions.users.UserNotFoundException;
import org.park.model.entities.User;
import org.park.model.enums.Status;
import org.park.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public UserResponseDTO createUser(CreateUserRequestDTO createUserRequestDTO){
        if(!documentAlredyRegistered(createUserRequestDTO.document())){
            throw new RuntimeException("Documento ya registrado");
        }
        User user = new User();
        user.setDocument(createUserRequestDTO.document());
        user.setName(createUserRequestDTO.name());
        user.setEmail(createUserRequestDTO.email());
        user.setPhone(createUserRequestDTO.phone());
        userRepository.save(user);
        return new UserResponseDTO(user.getId(),user.getName(),user.getEmail(),user.getPhone(),user.getDocument());
    }

    public UserResponseDTO updateUser(UpdateUserRequestDTO updateUserRequestDTO){
        User user = getUserOrThrow(updateUserRequestDTO.id());
        user.setName(updateUserRequestDTO.name());
        user.setEmail(updateUserRequestDTO.email());
        user.setPhone(updateUserRequestDTO.phone());
        userRepository.save(user);
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
        //TODO obtener vehiculos

        return new UserProfileResponseDTO(user.getId(),user.getName(),user.getDocument(),user.getPhone(),user.getEmail(),null);
    }

    public UserResponseDTO getUserByDocument(String document){
        Optional<User> userOptional = userRepository.findByDocument(document);
        if (userOptional.isEmpty()) {
            throw new UserNotFoundException("Documento no encontrado");
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
    
    public boolean documentAlredyRegistered(String document){
        return userRepository.findByDocument(document).isPresent();
    }

    public User getUserOrThrow(UUID id){
        Optional<User> userOptional = userRepository.findById(id);
        if(userOptional.isEmpty()){
            throw new UserNotFoundException(id.toString());
        }
        return userOptional.get();
    }
}
