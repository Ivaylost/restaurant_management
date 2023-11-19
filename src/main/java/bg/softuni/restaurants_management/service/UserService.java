package bg.softuni.restaurants_management.service;

import bg.softuni.restaurants_management.model.dto.UserRegistrationDTO;
import bg.softuni.restaurants_management.model.entity.UserEntity;

import java.util.Optional;

public interface UserService {
    Optional<UserEntity> findUserByEmail(String email);

    boolean registerUser(UserRegistrationDTO userRegistrationDTO);
}
