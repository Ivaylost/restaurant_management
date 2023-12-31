package bg.softuni.restaurants_management.model.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
public class UserEntity extends BaseEntity {
    @Column(unique = true)
    private String email;

    private String password;
    private String firstName;
    private String lastName;
    private Boolean isActive;
    private String registrationToken;

    public Boolean getActive() {
        return isActive;
    }

    public UserEntity setActive(Boolean active) {
        isActive = active;
        return this;
    }

    public String getRegistrationToken() {
        return registrationToken;
    }

    public UserEntity setRegistrationToken(String registrationToken) {
        this.registrationToken = registrationToken;
        return this;
    }

    @ManyToMany(fetch = FetchType.EAGER)
    private List<Role> roles = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY,
    mappedBy = "user")
    private List<Reservation> reservations = new ArrayList<>();

    @ManyToMany(fetch = FetchType.EAGER)
    private List<Restaurant> restaurants = new ArrayList<>();

    public List<Reservation> getReservations() {
        return reservations;
    }

    public UserEntity setReservations(List<Reservation> reservations) {
        this.reservations = reservations;
        return this;
    }

    public List<Restaurant> getRestaurants() {
        return restaurants;
    }

    public UserEntity setRestaurants(List<Restaurant> restaurants) {
        this.restaurants = restaurants;
        return this;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public UserEntity setRoles(List<Role> roles) {
        this.roles = roles;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public UserEntity setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public UserEntity setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public UserEntity setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public UserEntity setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }
}
