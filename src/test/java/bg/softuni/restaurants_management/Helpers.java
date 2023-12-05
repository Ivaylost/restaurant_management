package bg.softuni.restaurants_management;

import bg.softuni.restaurants_management.model.entity.Restaurant;
import bg.softuni.restaurants_management.model.entity.Role;
import bg.softuni.restaurants_management.model.entity.UserEntity;
import bg.softuni.restaurants_management.model.enums.RoleEnums;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public class Helpers {
    public static UserEntity createTestUser() {

        Role roleAdmin = new Role().setRole(RoleEnums.ADMIN);
        Role roleManager = new Role().setRole(RoleEnums.MANAGER);

        return new UserEntity()
                .setFirstName("first")
                .setLastName("last")
                .setActive(false)
                .setEmail("test@test.com")
                .setPassword("password")
                .setRoles(List.of(roleAdmin, roleManager));
    }

    public static boolean containsAuthority(UserDetails userDetails, String expectedAuthority) {
        return userDetails
                .getAuthorities()
                .stream()
                .anyMatch(a -> expectedAuthority.equals(a.getAuthority()));
    }
}
