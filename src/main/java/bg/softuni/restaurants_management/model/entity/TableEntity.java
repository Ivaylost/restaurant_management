package bg.softuni.restaurants_management.model.entity;

import jakarta.persistence.*;

import bg.softuni.restaurants_management.model.entity.Restaurant;

import java.util.ArrayList;
import java.util.List;

@Table(name = "tables")
@Entity
public class TableEntity extends BaseEntity{
    @Column(nullable = false)
    private String name;
    @ManyToOne
    private Restaurant restaurant;

    @OneToMany(
            fetch = FetchType.LAZY,
            mappedBy = "table",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Reservation> reservations = new ArrayList<>();

    public List<Reservation> getReservations() {
        return reservations;
    }

    public TableEntity setReservations(List<Reservation> reservations) {
        this.reservations = reservations;
        return this;
    }

    public String getName() {
        return name;
    }

    public TableEntity setName(String name) {
        this.name = name;
        return this;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public TableEntity setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
        return this;
    }
}
