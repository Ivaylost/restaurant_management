package bg.softuni.restaurants_management.web;

import bg.softuni.restaurants_management.Helpers;
import bg.softuni.restaurants_management.config.SecurityConfiguration;
import bg.softuni.restaurants_management.model.dto.CreateAllReservationsDateBindingModel;
import bg.softuni.restaurants_management.model.dto.ReservationViewModel;
import bg.softuni.restaurants_management.model.dto.UserRegistrationBindingModel;
import bg.softuni.restaurants_management.model.entity.Reservation;
import bg.softuni.restaurants_management.model.entity.Restaurant;
import bg.softuni.restaurants_management.model.entity.TableEntity;
import bg.softuni.restaurants_management.model.enums.ReservationEnums;
import bg.softuni.restaurants_management.repository.ReservationRepository;
import bg.softuni.restaurants_management.repository.RestaurantRepository;
import bg.softuni.restaurants_management.repository.TableRepository;
import bg.softuni.restaurants_management.service.ReservationService;
import bg.softuni.restaurants_management.service.RestaurantService;
import bg.softuni.restaurants_management.service.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
//import org.junit.runner.RunWith;--------------------------
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc()
class HomeControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private RestaurantService restaurantService;
    @Autowired
    private ReservationService reservationService;
    @Autowired
    private UserService userService;
    @Autowired
    private RestaurantRepository restaurantRepository;
    @Autowired
    private TableRepository tableRepository;
    @Autowired
    private ReservationRepository reservationRepository;

    @BeforeEach
    void setUp() {
        reservationRepository.deleteAll();
        tableRepository.deleteAll();
        restaurantRepository.deleteAll();
        tableRepository.deleteAll();
        restaurantRepository.deleteAll();
    }

    @AfterEach
    void tearDown() {
        reservationRepository.deleteAll();
        tableRepository.deleteAll();
        restaurantRepository.deleteAll();
        tableRepository.deleteAll();
        reservationRepository.deleteAll();
    }

    @Test
    void testIndex() throws Exception {
        mockMvc.perform(
                        MockMvcRequestBuilders.get("/")
                ).andExpect(status().is2xxSuccessful())
                .andExpect(view().name("index"))
                .andExpect(model().attributeExists("allRestaurants"))
                .andExpect(model().attribute("allRestaurants", instanceOf(List.class)));
    }

    @Test
    @WithMockUser(username = "admin@test.com", password = "password", roles = "USER")
    void testUserReservation() throws Exception {
        Restaurant restaurant = new Restaurant().setName("restaurant").setLat(100D).setLon(200D);
        Restaurant savedRestaurant = restaurantRepository.save(restaurant);

        mockMvc.perform(
                        MockMvcRequestBuilders.get("/userReservation/{restaurantId}", savedRestaurant.getId())
                )
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("create-reservation"))
                .andExpect(model().attributeExists("restaurantId"))
                .andExpect(model().attribute("restaurantName", savedRestaurant.getName()));
    }

    @Test
    @WithMockUser(username = "admin@test.com", password = "password", roles = "MANAGER")
    void testUserReservationPostRequest() throws Exception {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        String date = LocalDate.now().format(formatter);

        Reservation reservation = new Reservation().setReservations(ReservationEnums.RESERVATION_12H_13H)
                .setDate(LocalDate.now());
        //Reservation savedReservation = reservationRepository.save(reservation);

        TableEntity table = new TableEntity().setName("T1");
        table.getReservations().add(reservation);
        TableEntity savedTable = tableRepository.save(table);

        Restaurant restaurant = new Restaurant()
                .setName("restaurant")
                .setLon(100D)
                .setLat(200D);
        restaurant.getTableEntities().add(savedTable);
        Restaurant savedRestaurant = restaurantRepository.save(restaurant);
        mockMvc.perform(
                        MockMvcRequestBuilders.post("/userReservation/{restaurantId}", savedRestaurant.getId())
                                .param("datepicker", date)
                                .with(csrf())
                ).andExpect(status().is2xxSuccessful())
                .andExpect(view().name("make-reservation"))
                .andExpect(model().attributeExists("reservationViews"))
                .andExpect(model().attribute("reservationViews", instanceOf(List.class)));

    }
}