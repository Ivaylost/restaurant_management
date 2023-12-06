package bg.softuni.restaurants_management.web;

import bg.softuni.restaurants_management.model.entity.Restaurant;
import bg.softuni.restaurants_management.repository.RestaurantRepository;
import bg.softuni.restaurants_management.service.impl.RestaurantServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
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

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private RestaurantRepository restaurantRepository;

    @BeforeEach
    void setUp() {
        restaurantRepository.deleteAll();
    }

    @AfterEach
    void tearDown() {
        restaurantRepository.deleteAll();
    }

    @Test
    void testCoordinates() throws Exception {
        Restaurant restaurant = new Restaurant().setName("restaurant")
                .setLon(100D)
                .setLat(200D);
        restaurantRepository.save(restaurant);
        List<Restaurant> all = restaurantRepository.findAll();

        this.mockMvc.perform(MockMvcRequestBuilders.get("/coordinates/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0]", is(100D)))
                .andExpect(jsonPath("$[1]", is(200D)));
    }
}
