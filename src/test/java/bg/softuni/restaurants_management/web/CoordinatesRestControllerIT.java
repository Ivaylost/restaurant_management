package bg.softuni.restaurants_management.web;

import bg.softuni.restaurants_management.init.InitialInit;
import bg.softuni.restaurants_management.model.entity.Restaurant;
import bg.softuni.restaurants_management.repository.ReservationRepository;
import bg.softuni.restaurants_management.repository.RestaurantRepository;
import bg.softuni.restaurants_management.repository.TableRepository;
import bg.softuni.restaurants_management.service.impl.RestaurantServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class CoordinatesRestControllerIT {
    @SpyBean
    InitialInit initialInit;

    @Autowired
    private MockMvc mockMvc;
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
    }


    @AfterEach
    void tearDown() {
        reservationRepository.deleteAll();
        tableRepository.deleteAll();
        restaurantRepository.deleteAll();
    }

    @Test
    void testCoordinates() throws Exception {
        Restaurant restaurant = new Restaurant().setName("restaurant")
                .setLon(100D)
                .setLat(200D);
        Restaurant saved = restaurantRepository.save(restaurant);

        this.mockMvc.perform(MockMvcRequestBuilders.get("/coordinates/{id}", saved.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0]", is(100D)))
                .andExpect(jsonPath("$[1]", is(200D)));
    }
}
