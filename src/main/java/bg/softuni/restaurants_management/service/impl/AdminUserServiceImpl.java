package bg.softuni.restaurants_management.service.impl;

import bg.softuni.restaurants_management.model.dto.UserDto;
import bg.softuni.restaurants_management.model.entity.UserEntity;
import bg.softuni.restaurants_management.repository.AdminUserRepository;
import bg.softuni.restaurants_management.service.AdminUserService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdminUserServiceImpl implements AdminUserService {
    private final AdminUserRepository adminUserRepository;
    private final ModelMapper modelMapper;

    public AdminUserServiceImpl(AdminUserRepository adminUserRepository, ModelMapper modelMapper) {
        this.adminUserRepository = adminUserRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<UserDto> getAllUsers() {
        return adminUserRepository.findAll().stream()
                .map(userEntity ->
                        modelMapper.map(userEntity, UserDto.class)
                ).toList();
    }

    @Override
    public UserDto getUserById(Long id) {
        Optional<UserEntity> user = adminUserRepository.findById(id);
        if (user.isPresent()){
           return modelMapper.map(user, UserDto.class);
        }
        return null;
    }
}
