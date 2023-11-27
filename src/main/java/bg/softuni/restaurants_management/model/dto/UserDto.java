package bg.softuni.restaurants_management.model.dto;

import bg.softuni.restaurants_management.model.entity.Role;
import bg.softuni.restaurants_management.model.enums.RoleEnums;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class UserDto {

    private Long id;
    private String fullName;
    private String email;
    private List<Role> roles;

    public String getRolesAsString()  {
           return roles.stream().map(Role::getRole)
                    .map(RoleEnums::name)
                    .collect(Collectors.joining(""));
        }

    public List<Role> getRoles() {
        return roles;
    }

    public UserDto setRoles(List<Role> roles) {
        this.roles = roles;
        return this;
    }

    public Long getId() {
        return id;
    }

    public UserDto setId(Long id) {
        this.id = id;
        return this;
    }

    public String getFullName() {
        return fullName;
    }

    public UserDto setFullName(String fullName) {
        this.fullName = fullName;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public UserDto setEmail(String email) {
        this.email = email;
        return this;
    }
}
