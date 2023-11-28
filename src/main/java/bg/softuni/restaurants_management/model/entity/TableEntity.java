package bg.softuni.restaurants_management.model.entity;

import jakarta.persistence.*;

import bg.softuni.restaurants_management.model.entity.Restaurant;

@Table(name = "tables")
@Entity
public class TableEntity extends BaseEntity{
    @Column(nullable = false)
    private String name;
    @ManyToOne
    private Restaurant restaurant;

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
