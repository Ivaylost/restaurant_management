package bg.softuni.restaurants_management.init;

import bg.softuni.restaurants_management.model.entity.ReservationSlot;
import bg.softuni.restaurants_management.model.entity.Role;
import bg.softuni.restaurants_management.model.entity.UserEntity;
import bg.softuni.restaurants_management.model.enums.ReservationEnums;
import bg.softuni.restaurants_management.model.enums.RoleEnums;
import bg.softuni.restaurants_management.repository.ReservationSlotRepository;
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
    private final ReservationSlotRepository reservationSlotRepository;

    public InitialInit(@Value("${restaurants_management.default.admin.pass}") String defaultAdminPass, RoleRepository roleRepository, UserRepository userRepository, PasswordEncoder passwordEncoder, ReservationSlotRepository reservationSlotRepository) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.defaultAdminPass = defaultAdminPass;
        this.reservationSlotRepository = reservationSlotRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        long count = roleRepository.count();
        long reservationCount = reservationSlotRepository.count();

        if (reservationCount == 0) {
            List<ReservationSlot> reservationSlots = new ArrayList<>();
            Arrays.stream(ReservationEnums.values())
                    .forEach(reservationSlot -> {
                        ReservationSlot reservationItem = new ReservationSlot();
                        reservationItem.setReservationSlot(reservationSlot);
                        reservationSlots.add(reservationItem);
                    });
            reservationSlotRepository.saveAll(reservationSlots);
        }

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
