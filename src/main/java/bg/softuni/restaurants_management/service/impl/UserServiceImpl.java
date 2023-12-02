package bg.softuni.restaurants_management.service.impl;

import bg.softuni.restaurants_management.error.ObjectNotFoundException;
import bg.softuni.restaurants_management.model.dto.RestaurantViewDetails;
import bg.softuni.restaurants_management.model.dto.UserRegistrationBindingModel;
import bg.softuni.restaurants_management.model.entity.UserEntity;
import bg.softuni.restaurants_management.model.enums.RoleEnums;
import bg.softuni.restaurants_management.repository.RoleRepository;
import bg.softuni.restaurants_management.repository.UserRepository;
import bg.softuni.restaurants_management.service.EventPublisherInterface;
import bg.softuni.restaurants_management.service.UserService;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final EventPublisherInterface eventPublisher;
    private final RoleRepository roleRepository;


    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper, EventPublisherInterface eventPublisher, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.eventPublisher = eventPublisher;
        this.roleRepository = roleRepository;
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
        String registrationToken = UUID.randomUUID().toString();
        user.setRegistrationToken(registrationToken);
        userRepository.save(user);
        String registrationLink = "http://localhost:8080/users/verify?token=" + user.getRegistrationToken();
        eventPublisher.publishUserRegistrationEvent(registrationLink);
        return true;
    }

    @Override
    public List<RestaurantViewDetails> getUsersRestaurants(UserEntity loggedUser) {

        return loggedUser.getRestaurants().stream()
                .map(x -> modelMapper.map(x, RestaurantViewDetails.class))
                .toList();
    }

    @Override
    @Transactional
    public void verifyUser(String token) {
        Optional<UserEntity> optionalUser = userRepository.findByRegistrationToken(token);
        if (optionalUser.isEmpty()){
            throw new ObjectNotFoundException("User not found");
        }
        UserEntity userEntity = optionalUser.get();
        userEntity.getRoles().add((roleRepository.findByRole(RoleEnums.USER)));
        userEntity.setActive(true);
        userRepository.save(optionalUser.get().setActive(true));
    }
}
