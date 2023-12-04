package bg.softuni.restaurants_management.service.impl;

import bg.softuni.restaurants_management.Helpers;
import bg.softuni.restaurants_management.model.entity.Restaurant;
import bg.softuni.restaurants_management.model.entity.UserEntity;
import bg.softuni.restaurants_management.repository.RestaurantRepository;
import bg.softuni.restaurants_management.repository.RoleRepository;
import bg.softuni.restaurants_management.repository.UserRepository;
import bg.softuni.restaurants_management.service.EventPublisherInterface;
import bg.softuni.restaurants_management.service.RestaurantService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

@SpringBootTest
public class UserServiceImplIT {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private EventPublisherInterface eventPublisher;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private RestaurantRepository restaurantRepository;

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
        restaurantRepository.deleteAll();
        Restaurant restaurant = new Restaurant()
                .setName("restaurant")
                .setActive(true)
                .setImgUrl("url")
                .setLat(1D)
                .setLon(1D);
        restaurantRepository.save(restaurant);
        UserEntity testUser = new UserEntity()
                .setFirstName("first")
                .setLastName("last")
                .setActive(false)
                .setEmail("test@test.com")
                .setPassword("password")
                .setRestaurants(List.of(restaurant));
        userRepository.save(testUser);
    }

    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
    }

    @Test
    void testFindUserByEmail() {
        String email = "test@test.com";
        Optional<UserEntity> byEmail = userRepository.findByEmail(email);
        Assertions.assertTrue(byEmail.isPresent());
        UserEntity userEntity = byEmail.get();
        Assertions.assertEquals("first", userEntity.getFirstName());
        Assertions.assertEquals("last", userEntity.getLastName());
        Assertions.assertEquals("test@test.com", userEntity.getEmail());
        Assertions.assertEquals(false, userEntity.getActive());
    }
}
