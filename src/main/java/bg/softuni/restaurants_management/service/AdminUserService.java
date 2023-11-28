package bg.softuni.restaurants_management.service;

import bg.softuni.restaurants_management.model.dto.UserDto;
import bg.softuni.restaurants_management.model.entity.Restaurant;
import bg.softuni.restaurants_management.model.entity.Role;

import java.util.List;

public interface AdminUserService {
    List<UserDto> getAllUsers();

    UserDto getUserById(Long id);

    List<Role> getUnassignedRoles(Long userId);

    void delete(Long userId, Long roleId);

    void assignRole(Long userId, Long roleId);

    Long getUserByEmail(String email);

    List<Restaurant> getUnassignedRestaurants(Long userId);

    void assignRestaurant(Long userId, Long restaurantId);

    void unassignRestaurant(Long userId, Long restaurantId);
}
