package bg.softuni.restaurants_management.model.dto;

import bg.softuni.restaurants_management.model.entity.Role;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public class UserDto {

    private Long id;
    private String fullName;
    private String email;

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
