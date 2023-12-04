package bg.softuni.restaurants_management;

import bg.softuni.restaurants_management.model.entity.Restaurant;
import bg.softuni.restaurants_management.model.entity.Role;
import bg.softuni.restaurants_management.model.entity.UserEntity;
import bg.softuni.restaurants_management.model.enums.RoleEnums;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public class Helpers {
    public static UserEntity createTestUser(){


        return new UserEntity()
                .setFirstName("first")
                .setLastName("last")
                .setActive(false)
                .setEmail("test@test.com")
                .setPassword("password");
    }

    public static boolean containsAuthority(UserDetails userDetails, String expectedAuthority){
        return userDetails
                .getAuthorities()
                .stream()
                .anyMatch(a -> expectedAuthority.equals(a.getAuthority()));
    }
}
