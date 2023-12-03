package bg.softuni.restaurants_management.service.impl;

import bg.softuni.restaurants_management.model.entity.Role;
import bg.softuni.restaurants_management.model.entity.UserEntity;
import bg.softuni.restaurants_management.model.enums.RoleEnums;
import bg.softuni.restaurants_management.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RestaurantsManagementUserDetailsServiceTest {
    private RestaurantsManagementUserDetailsService serviceToTest;
    @Mock
    private UserRepository mockUserRepository;

    @BeforeEach
    void setUp() {
        serviceToTest = new RestaurantsManagementUserDetailsService(
                mockUserRepository
        );
    }

    @Test
    public void testUserNotFound() {
        Assertions.assertThrows(
                UsernameNotFoundException.class,
                () -> serviceToTest.loadUserByUsername("test@test.com")
        );
    }

    @Test
    public void testUserFoundException(){
        //Arrange
        UserEntity userEntity = createTestUser();
        when(mockUserRepository.findByEmail(userEntity.getEmail()))
                .thenReturn(Optional.of(userEntity));

        //Act
        UserDetails userDetails = serviceToTest.loadUserByUsername("test@test.com");

        //Assert
        Assertions.assertNotNull(userDetails);
        Assertions.assertEquals(userEntity.getEmail(), userDetails.getUsername());
        Assertions.assertEquals(userEntity.getPassword(), userDetails.getPassword());
        Assertions.assertEquals(2, userDetails.getAuthorities().size());
        Assertions.assertTrue(containsAuthority(userDetails, "ROLE_" + RoleEnums.ADMIN.name()));
        Assertions.assertTrue(containsAuthority(userDetails, "ROLE_" + RoleEnums.MANAGER.name()));
    }

    private static UserEntity createTestUser(){

        Role roleAdmin = new Role().setRole(RoleEnums.ADMIN);
        Role roleManager = new Role().setRole(RoleEnums.MANAGER);

        return new UserEntity()
                .setFirstName("first")
                .setLastName("last")
                .setActive(false)
                .setEmail("test@test.com")
                .setRoles(List.of(roleAdmin, roleManager))
                .setPassword("password");
    }

    private boolean containsAuthority(UserDetails userDetails, String expectedAuthority){
        return userDetails
                .getAuthorities()
                .stream()
                .anyMatch(a -> expectedAuthority.equals(a.getAuthority()));
    }
}
