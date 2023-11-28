package bg.softuni.restaurants_management.model.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;

public class TableCreateBindingModel {
    @NotEmpty
    private String name;

    @Min(value = 1, message = "Please select a restaurant")
    private Long restaurantId;

    public Long getRestaurantId() {
        return restaurantId;
    }

    public TableCreateBindingModel setRestaurantId(Long restaurantId) {
        this.restaurantId = restaurantId;
        return this;
    }

    public String getName() {
        return name;
    }

    public TableCreateBindingModel setName(String name) {
        this.name = name;
        return this;
    }
}
