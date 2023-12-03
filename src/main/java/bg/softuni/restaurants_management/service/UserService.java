package bg.softuni.restaurants_management.service;

import bg.softuni.restaurants_management.model.dto.RestaurantViewDetails;
import bg.softuni.restaurants_management.model.dto.UserRegistrationBindingModel;
import bg.softuni.restaurants_management.model.entity.UserEntity;

import java.util.List;
import java.util.Optional;

public interface UserService {
    UserEntity findUserByEmail(String email);

    boolean registerUser(UserRegistrationBindingModel userRegistrationBindingModel);

    List<RestaurantViewDetails> getUsersRestaurants(UserEntity loggedUser);

    void verifyUser(String token);
}
