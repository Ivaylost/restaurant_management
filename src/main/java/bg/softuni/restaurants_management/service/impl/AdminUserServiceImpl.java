package bg.softuni.restaurants_management.service.impl;

import bg.softuni.restaurants_management.model.dto.UserDto;
import bg.softuni.restaurants_management.model.entity.Role;
import bg.softuni.restaurants_management.model.entity.UserEntity;
import bg.softuni.restaurants_management.repository.AdminUserRepository;
import bg.softuni.restaurants_management.repository.RoleRepository;
import bg.softuni.restaurants_management.service.AdminUserService;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Service
public class AdminUserServiceImpl implements AdminUserService {
    private final AdminUserRepository adminUserRepository;
    private final RoleRepository roleRepository;
    private final ModelMapper modelMapper;

    public AdminUserServiceImpl(AdminUserRepository adminUserRepository, RoleRepository roleRepository, ModelMapper modelMapper) {
        this.adminUserRepository = adminUserRepository;
        this.roleRepository = roleRepository;
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

    @Override
    public List<Role> getUnassignedRoles(Long id) {
        Optional<UserEntity> user = adminUserRepository.findById(id);
        if (user.isPresent()){
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
    @Transactional
    public void delete(Long userId, Long roleId) {
        Optional<UserEntity> optionalUser = adminUserRepository.findById(userId);
        if (optionalUser.isPresent()){
            UserEntity userEntity = optionalUser.get();
            Role role = roleRepository.findById(roleId).get();
            userEntity.getRoles().remove(role);
            adminUserRepository.save(userEntity);
        }
    }

    @Override
    @Transactional
    public void assignRole(Long userId, Long roleId) {
        Optional<UserEntity> optionalUser = adminUserRepository.findById(userId);
        if (optionalUser.isPresent()){
            UserEntity userEntity = optionalUser.get();
            Role role = roleRepository.findById(roleId).get();
            if (!userEntity.getRoles().contains(role)){
                userEntity.getRoles().add(role);
                adminUserRepository.save(userEntity);
            }
        }
    }

    private List<Role> getAllRoles() {
        return roleRepository.findAll();
    }
}
