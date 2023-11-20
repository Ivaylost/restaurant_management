package bg.softuni.restaurants_management.init;

import bg.softuni.restaurants_management.model.entity.Role;
import bg.softuni.restaurants_management.model.entity.UserEntity;
import bg.softuni.restaurants_management.model.enums.RoleEnums;
import bg.softuni.restaurants_management.repository.RoleRepository;
import bg.softuni.restaurants_management.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class InitialInit implements CommandLineRunner {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final String defaultAdminPass;

    public InitialInit(@Value("${restaurants_management.default.admin.pass}") String defaultAdminPass, RoleRepository roleRepository, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.defaultAdminPass = defaultAdminPass;
    }

    @Override
    public void run(String... args) throws Exception {
        long count = roleRepository.count();

        if (count == 0) {
            List<Role> roles = new ArrayList<>();
            Arrays.stream(RoleEnums.values())
                    .forEach(role -> {
                        Role roleItem = new Role();
                        roleItem.setRole(role);
                        roles.add(roleItem);
                    });
            roleRepository.saveAll(roles);
        }

        if (userRepository.findByEmail("admin@example.com").isEmpty()) {
            List<Role> adminRoles = List.of(roleRepository.findByRole(RoleEnums.ADMIN));
            UserEntity admin = new UserEntity();
            admin.setEmail("admin@example.com");
            admin.setFirstName("Admin");
            admin.setLastName("Admin");
            admin.setPassword(passwordEncoder.encode(defaultAdminPass));
            admin.setRoles(adminRoles);
            userRepository.saveAndFlush(admin);
        }
    }
}
