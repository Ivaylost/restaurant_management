package bg.softuni.restaurants_management.service.impl;

import bg.softuni.restaurants_management.Helpers;
import bg.softuni.restaurants_management.model.dto.CreateAllReservationsDateBindingModel;
import bg.softuni.restaurants_management.model.entity.Reservation;
import bg.softuni.restaurants_management.model.entity.Restaurant;
import bg.softuni.restaurants_management.model.entity.TableEntity;
import bg.softuni.restaurants_management.model.entity.UserEntity;
import bg.softuni.restaurants_management.model.enums.ReservationEnums;
import bg.softuni.restaurants_management.repository.ReservationRepository;
import bg.softuni.restaurants_management.repository.RestaurantRepository;
import bg.softuni.restaurants_management.repository.TableRepository;
import bg.softuni.restaurants_management.repository.UserRepository;
import bg.softuni.restaurants_management.service.ReservationService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@ExtendWith(MockitoExtension.class)
class ReservationServiceImplTest {
    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private TableRepository tableRepository;
    @Autowired
    private RestaurantRepository restaurantRepository;
    @Autowired
    private ReservationService reservationService;
    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        reservationRepository.deleteAll();
        tableRepository.deleteAll();
        restaurantRepository.deleteAll();
        userRepository.deleteAll();
    }

    @AfterEach
    void tearDown() {
        reservationRepository.deleteAll();
        tableRepository.deleteAll();
        restaurantRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    void testInitReservationByRestaurant() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        String date = LocalDate.now().format(formatter);

        TableEntity table = new TableEntity().setName("T1");
        TableEntity savedTable = tableRepository.save(table);

        Restaurant restaurant = new Restaurant()
                .setName("restaurant")
                .setLon(100D)
                .setLat(200D);
        restaurant.getTableEntities().add(savedTable);
        Restaurant savedRestaurant = restaurantRepository.save(restaurant);
        TableEntity table1 = tableRepository.findById(savedTable.getId()).orElseThrow();
        table1.setRestaurant(savedRestaurant);
        tableRepository.save(table1);

        CreateAllReservationsDateBindingModel dto = new CreateAllReservationsDateBindingModel()
                .setRestaurantId(savedRestaurant.getId())
                .setDatepicker(date);

        reservationService.initReservationByRestaurant(dto);
        List<Reservation> all = reservationRepository.findAll();
        assertEquals(Arrays.stream(ReservationEnums.values()).count(), all.size());
        for(Reservation reservation:all){
            assertEquals(date, reservation.getDate().format(formatter));
            assertNull(reservation.getUser());
            assertTrue(Arrays.stream(ReservationEnums.values()).anyMatch(x->reservation.getReservations()==x));
        }
    }

    @Test
    void testCreateReservationForActiveRestaurantsForDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        LocalDate now = LocalDate.now();
        String date = now.format(formatter);

        TableEntity table = new TableEntity().setName("T1");
        TableEntity savedTable = tableRepository.save(table);

        Restaurant restaurant = new Restaurant()
                .setActive(true)
                .setName("restaurant")
                .setLon(100D)
                .setLat(200D);
        restaurant.getTableEntities().add(savedTable);
        Restaurant savedRestaurant = restaurantRepository.save(restaurant);
        TableEntity table1 = tableRepository.findById(savedTable.getId()).orElseThrow();
        table1.setRestaurant(savedRestaurant);
        tableRepository.save(table1);

        int reservationForActiveRestaurantsForDate = reservationService.createReservationForActiveRestaurantsForDate(now);
        List<Reservation> all = reservationRepository.findAll();
        assertEquals(Arrays.stream(ReservationEnums.values()).count(), reservationForActiveRestaurantsForDate);
        assertEquals(Arrays.stream(ReservationEnums.values()).count(), all.size());
        for (Reservation reservation : all) {
            assertEquals(date, reservation.getDate().format(formatter));
            assertNull(reservation.getUser());
            assertTrue(Arrays.stream(ReservationEnums.values()).anyMatch(x -> reservation.getReservations() == x));
        }
    }

        @Test
        void testMakeReservation() {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
            LocalDate now = LocalDate.now();

            UserEntity testUser = new UserEntity()
                    .setFirstName("first")
                    .setLastName("last")
                    .setActive(false)
                    .setEmail("test@test.com")
                    .setPassword("password");
            userRepository.save(testUser);

            Reservation reservation =
                    new Reservation().setDate(now).setReservations(ReservationEnums.RESERVATION_12H_13H);
            Reservation saved = reservationRepository.save(reservation);

            reservationService.makeReservation(saved.getId(), testUser.getEmail());

            Reservation updatedReservation = reservationRepository.findById(saved.getId()).orElseThrow();

            assertEquals(testUser.getId(), updatedReservation.getUser().getId());
            assertEquals(ReservationEnums.RESERVATION_12H_13H, updatedReservation.getReservations());
    }
}