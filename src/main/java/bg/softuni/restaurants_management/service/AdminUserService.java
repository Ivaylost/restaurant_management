package bg.softuni.restaurants_management.service;

import bg.softuni.restaurants_management.model.dto.UserDto;

import java.util.List;

public interface AdminUserService {
    List<UserDto> getAllUsers();

    UserDto getUserById(Long id);
}
