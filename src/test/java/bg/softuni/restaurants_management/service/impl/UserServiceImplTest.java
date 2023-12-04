package bg.softuni.restaurants_management.service.impl;

import bg.softuni.restaurants_management.Helpers;
import bg.softuni.restaurants_management.error.ObjectNotFoundException;
import bg.softuni.restaurants_management.model.dto.RestaurantViewDetails;
import bg.softuni.restaurants_management.model.entity.Restaurant;
import bg.softuni.restaurants_management.model.entity.UserEntity;
import bg.softuni.restaurants_management.repository.RoleRepository;
import bg.softuni.restaurants_management.repository.UserRepository;
import bg.softuni.restaurants_management.service.EventPublisherInterface;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

    private UserServiceImpl serviceToTest;
    @Mock
    private UserRepository mockUserRepository;
    @Mock
    private EventPublisherInterface mockEventPublisher;
    @Mock
    private RoleRepository mockRoleRepository;

    private ModelMapper modelMapper;

    @BeforeEach
    void setUp() {
        serviceToTest = new UserServiceImpl(
                mockUserRepository,
                modelMapper,
                mockEventPublisher,
                mockRoleRepository
        );

        this.modelMapper = new ModelMapper();
    }

    @Test
    public void testFindUserByEmailThrowObjectNotFoundException() {
        Assertions.assertThrows(
                ObjectNotFoundException.class,
                () -> serviceToTest.findUserByEmail("test@test.com")
        );
    }

    @Test
    public void testFindUserByEmailReturnUser() {
        UserEntity userEntity = Helpers.createTestUser();
        when(mockUserRepository.findByEmail(userEntity.getEmail()))
                .thenReturn(Optional.of(userEntity));

        UserEntity user = serviceToTest.findUserByEmail("test@test.com");

        Assertions.assertNotNull(user);
        Assertions.assertEquals(user.getEmail(), userEntity.getEmail());
        Assertions.assertEquals(user.getPassword(), userEntity.getPassword());
        Assertions.assertEquals(user.getRoles(), userEntity.getRoles());
        Assertions.assertEquals(user.getActive(), userEntity.getActive());
        Assertions.assertEquals(user.getFirstName(), userEntity.getFirstName());
        Assertions.assertEquals(user.getLastName(), userEntity.getLastName());
    }

    @Test
    public void testGetUsersRestaurants() {
        UserEntity testUser = new UserEntity()
                .setFirstName("first")
                .setLastName("last")
                .setActive(false)
                .setEmail("test@test.com")
                .setPassword("password");
        String restaurantName = "restaurant";
        Restaurant restaurant = new Restaurant().setName("restaurant");
        testUser.getRestaurants().add(restaurant);
        RestaurantViewDetails restaurantViewDetails = new RestaurantViewDetails().setName(restaurantName);

        when(modelMapper.map(testUser, RestaurantViewDetails.class))
                .thenReturn(restaurantViewDetails);

        List<RestaurantViewDetails> usersRestaurants = serviceToTest.getUsersRestaurants(testUser);

        Assertions.assertEquals(1, usersRestaurants.size());
        Assertions.assertEquals(restaurantName, usersRestaurants.get(0).getName());

    }
}