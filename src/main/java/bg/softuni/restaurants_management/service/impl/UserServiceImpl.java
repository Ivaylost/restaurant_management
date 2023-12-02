package bg.softuni.restaurants_management.service.impl;

import bg.softuni.restaurants_management.error.ObjectNotFoundException;
import bg.softuni.restaurants_management.model.dto.RestaurantViewDetails;
import bg.softuni.restaurants_management.model.dto.UserRegistrationBindingModel;
import bg.softuni.restaurants_management.model.entity.UserEntity;
import bg.softuni.restaurants_management.repository.UserRepository;
import bg.softuni.restaurants_management.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public Optional<UserEntity> findUserByEmail(String email) {

        Optional<UserEntity> byEmail = userRepository.findByEmail(email);
        if (byEmail.isEmpty()){
            throw new ObjectNotFoundException("User not found");
        }
        return byEmail;
    }

    @Override
    public boolean registerUser(UserRegistrationBindingModel userRegistrationBindingModel) {
        UserEntity user = modelMapper.map(userRegistrationBindingModel, UserEntity.class);
        userRepository.save(user);
        return true;
    }

    @Override
    public List<RestaurantViewDetails> getUsersRestaurants(UserEntity loggedUser) {

        return loggedUser.getRestaurants().stream()
                .map(x -> modelMapper.map(x, RestaurantViewDetails.class))
                .toList();
    }
}
