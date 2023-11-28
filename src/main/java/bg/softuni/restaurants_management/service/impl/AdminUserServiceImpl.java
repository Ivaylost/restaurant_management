package bg.softuni.restaurants_management.service.impl;

import bg.softuni.restaurants_management.model.dto.UserDto;
import bg.softuni.restaurants_management.model.entity.Restaurant;
import bg.softuni.restaurants_management.model.entity.Role;
import bg.softuni.restaurants_management.model.entity.UserEntity;
import bg.softuni.restaurants_management.model.enums.RoleEnums;
import bg.softuni.restaurants_management.repository.RestaurantRepository;
import bg.softuni.restaurants_management.repository.RoleRepository;
import bg.softuni.restaurants_management.repository.UserRepository;
import bg.softuni.restaurants_management.service.AdminUserService;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Service
public class AdminUserServiceImpl implements AdminUserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final RestaurantRepository restaurantRepository;
    private final ModelMapper modelMapper;

    public AdminUserServiceImpl(UserRepository userRepository,
                                RoleRepository roleRepository,
                                RestaurantRepository restaurantRepository,
                                ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.restaurantRepository = restaurantRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<UserDto> getAllUsers() {
        return userRepository.findAll().stream()
                .map(userEntity ->
                        modelMapper.map(userEntity, UserDto.class)
                ).toList();
    }

    @Override
    public UserDto getUserById(Long id) {
        Optional<UserEntity> user = userRepository.findById(id);
        if (user.isPresent()) {
            return modelMapper.map(user, UserDto.class);
        }
        return null;
    }

    @Override
    public List<Role> getUnassignedRoles(Long id) {
        Optional<UserEntity> user = userRepository.findById(id);
        if (user.isPresent()) {
            List<Role> allRoles = getAllRoles();
            List<Role> roles = user.get().getRoles();
            return Stream.concat(
                    allRoles.stream().filter(e -> !roles.contains(e)),
                    roles.stream().filter(e -> !allRoles.contains(e))
            ).toList();
        }
        return null;
    }

    @Override
    public List<Restaurant> getUnassignedRestaurants(Long userId) {
        Optional<UserEntity> user = userRepository.findById(userId);
        if (user.isPresent()) {
            List<Restaurant> allRestaurants = getAllRestaurants();
            List<Restaurant> restaurants = user.get().getRestaurants();
            return Stream.concat(
                    allRestaurants.stream().filter(e -> !restaurants.contains(e)),
                    restaurants.stream().filter(e -> !allRestaurants.contains(e))
            ).toList();
        }
        return null;
    }

    @Override
    public void assignRestaurant(Long userId, Long restaurantId) {
        Optional<UserEntity> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()) {
            UserEntity userEntity = optionalUser.get();
            Restaurant restaurant = restaurantRepository.findById(restaurantId).get();
            if (!userEntity.getRestaurants().contains(restaurant)) {
                userEntity.getRestaurants().add(restaurant);
                userRepository.save(userEntity);
            }
        }
    }

    @Override
    @Transactional
    public void unassignRestaurant(Long userId, Long restaurantId) {
        Optional<UserEntity> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()) {
            UserEntity userEntity = optionalUser.get();
            Restaurant restaurant = restaurantRepository.findById(restaurantId).get();
            userEntity.getRestaurants().remove(restaurant);
            userRepository.save(userEntity);
        }
    }


    @Override
    @Transactional
    public void removeRole(Long userId, Long roleId) {
        Optional<UserEntity> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()) {
            UserEntity userEntity = optionalUser.get();
            Role role = roleRepository.findById(roleId).get();
            userEntity.getRoles().remove(role);

            if (role.getRole() == RoleEnums.MANAGER){
                userEntity.setRestaurants(new ArrayList<>());
            }

            userRepository.save(userEntity);
        }
    }

    @Override
    @Transactional
    public void assignRole(Long userId, Long roleId) {
        Optional<UserEntity> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()) {
            UserEntity userEntity = optionalUser.get();
            Role role = roleRepository.findById(roleId).get();
            if (!userEntity.getRoles().contains(role)) {
                userEntity.getRoles().add(role);
                userRepository.save(userEntity);
            }
        }
    }

    @Override
    public Long getUserByEmail(String email) {
        Optional<UserEntity> optionalUser = userRepository.findByEmail(email);
        return optionalUser.<Long>map(UserEntity::getId).orElse(null);
    }

    private List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    private List<Restaurant> getAllRestaurants() {
        return restaurantRepository.findAll();
    }

}
