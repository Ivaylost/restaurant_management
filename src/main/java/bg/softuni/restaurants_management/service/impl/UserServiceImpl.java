package bg.softuni.restaurants_management.service.impl;

import bg.softuni.restaurants_management.model.dto.UserRegistrationBindingModel;
import bg.softuni.restaurants_management.model.entity.Role;
import bg.softuni.restaurants_management.model.entity.UserEntity;
import bg.softuni.restaurants_management.model.enums.RoleEnums;
import bg.softuni.restaurants_management.repository.RoleRepository;
import bg.softuni.restaurants_management.repository.UserRepository;
import bg.softuni.restaurants_management.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }

    @Override
    public Optional<UserEntity> findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public boolean registerUser(UserRegistrationBindingModel userRegistrationBindingModel) {
        userRepository.save(map(userRegistrationBindingModel));
        return true;
    }

    private UserEntity map(UserRegistrationBindingModel userRegistrationBindingModel) {

        List<Role> userRole = List.of(roleRepository.findByRole(RoleEnums.USER));

        return new UserEntity()
                .setFirstName(userRegistrationBindingModel.getFirstName())
                .setLastName(userRegistrationBindingModel.getLastName())
                .setRoles(userRole)
                .setEmail(userRegistrationBindingModel.getEmail())
                .setPassword(passwordEncoder.encode(userRegistrationBindingModel.getPassword()));
    }
}
