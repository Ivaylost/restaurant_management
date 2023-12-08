package bg.softuni.restaurants_management.init;

import bg.softuni.restaurants_management.model.entity.*;
import bg.softuni.restaurants_management.model.enums.ReservationEnums;
import bg.softuni.restaurants_management.model.enums.RoleEnums;
import bg.softuni.restaurants_management.repository.*;
import bg.softuni.restaurants_management.service.ImageService;
import bg.softuni.restaurants_management.service.ReservationService;
import bg.softuni.restaurants_management.service.RestaurantService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ResourceLoader;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.nio.file.Files;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Component
public class InitialInit implements CommandLineRunner {

    private final RestaurantService restaurantService;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final String defaultAdminPass;
    private final ReservationSlotRepository reservationSlotRepository;
    private final RestaurantRepository restaurantRepository;
    private final ImageService imageService;
    private final ResourceLoader resourceLoader;
    private final TableRepository tableRepository;
    private final ReservationService reservationService;


    public InitialInit(RestaurantService restaurantService, @Value("${restaurants_management.default.admin.pass}") String defaultAdminPass,
                       RoleRepository roleRepository,
                       UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       ReservationSlotRepository reservationSlotRepository,
                       RestaurantRepository restaurantRepository,
                       ImageService imageService,
                       ResourceLoader resourceLoader,
                       TableRepository tableRepository,
                       ReservationService reservationService
                       ) {
        this.restaurantService = restaurantService;
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.defaultAdminPass = defaultAdminPass;
        this.reservationSlotRepository = reservationSlotRepository;
        this.restaurantRepository = restaurantRepository;
        this.imageService = imageService;
        this.resourceLoader = resourceLoader;
        this.tableRepository = tableRepository;
        this.reservationService = reservationService;
    }

    @Override
    public void run(String... args) throws Exception {
        long count = roleRepository.count();
        long reservationCount = reservationSlotRepository.count();
        long restaurantCount = restaurantRepository.count();

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

        if (restaurantCount == 0) {
            Restaurant restaurant = new Restaurant().setName("Brother's restaurant")
                    .setLon(23.31891732089949D)
                    .setLat(42.684754219143535D)
                    .setActive(true);
            Restaurant saved = restaurantRepository.save(restaurant);
            TableEntity table = new TableEntity().setName("T1").setRestaurant(saved);
            TableEntity savedTable = tableRepository.save(table);
            reservationService.createReservationForActiveRestaurantsForDate(LocalDate.now());

            Resource resource = resourceLoader.getResource("classpath:static/img/bar.jpg");
            InputStream inputStream = resource.getInputStream();
            byte[] fileContent = Files.readAllBytes(resource.getFile().toPath());
            MultipartFile multipartFile = new MockMultipartFile(Objects.requireNonNull(resource.getFilename()), fileContent);
            String filename = resource.getFilename();

            String[] suffix = filename.split("\\.");
            String pathToSaveImage = "src\\main\\resources\\static\\images\\Restaurant_" + saved.getId()
                    + "\\primaryImage." + suffix[1];
            String pathToSaveInRestaurant = "\\images\\Restaurant_" + saved.getId()
                    + "\\primaryImage." + suffix[1];

            imageService.saveImageIntoFileSystem(multipartFile, pathToSaveImage);
            restaurantService.updateImgUrl(saved, pathToSaveInRestaurant);
        }
    }
}
