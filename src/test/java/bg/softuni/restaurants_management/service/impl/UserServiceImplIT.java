package bg.softuni.restaurants_management.service.impl;

import bg.softuni.restaurants_management.model.dto.RestaurantViewDetails;
import bg.softuni.restaurants_management.model.dto.UserRegistrationBindingModel;
import bg.softuni.restaurants_management.model.entity.Restaurant;
import bg.softuni.restaurants_management.model.entity.UserEntity;
import bg.softuni.restaurants_management.repository.RestaurantRepository;
import bg.softuni.restaurants_management.repository.RoleRepository;
import bg.softuni.restaurants_management.repository.UserRepository;
import bg.softuni.restaurants_management.service.EventPublisherInterface;
import bg.softuni.restaurants_management.service.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationEventPublisher;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class UserServiceImplIT {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RestaurantRepository restaurantRepository;
    @Autowired
    private UserService userService;

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
        UserEntity userByEmail = userService.findUserByEmail(email);
        assertEquals("first", userByEmail.getFirstName());
        assertEquals("last", userByEmail.getLastName());
        assertEquals("test@test.com", userByEmail.getEmail());
        assertEquals(false, userByEmail.getActive());
    }

    @Test
    void testRegisterUser(){
        UserRegistrationBindingModel userBindingModel = new UserRegistrationBindingModel().setFirstName("Test")
                .setLastName("User")
                .setEmail("test@user.com")
                .setPassword("password");

        UserEntity userEntity = userService.registerUser(userBindingModel);

        assertEquals(userBindingModel.getFirstName(), userEntity.getFirstName());
        assertEquals(userBindingModel.getLastName(), userEntity.getLastName());
        assertEquals(userBindingModel.getEmail(), userEntity.getEmail());
    }

    @Test
    void testGetUsersRestaurants(){

        UserEntity userByEmail = userService.findUserByEmail("test@test.com");

        List<RestaurantViewDetails> usersRestaurants = userService.getUsersRestaurants(userByEmail);

        assertEquals(1, usersRestaurants.size());
        boolean result = usersRestaurants.stream()
                .map(RestaurantViewDetails::getName)
                .anyMatch("restaurant"::equals);
        assertTrue(result);
        assertInstanceOf(RestaurantViewDetails.class, usersRestaurants.get(0));
    }

    @Test
    void testVerifyUser(){

        UserRegistrationBindingModel userBindingModel = new UserRegistrationBindingModel().setFirstName("Test")
                .setLastName("User")
                .setEmail("test@user.com")
                .setPassword("password");

        UserEntity userEntity = userService.registerUser(userBindingModel);

        String registrationToken = userEntity.getRegistrationToken();

        userService.verifyUser(registrationToken);
        Optional<UserEntity> byEmail = userRepository.findByEmail(userEntity.getEmail());
        assertTrue(byEmail.isPresent());
        assertTrue(byEmail.get().getActive());
    }
}
