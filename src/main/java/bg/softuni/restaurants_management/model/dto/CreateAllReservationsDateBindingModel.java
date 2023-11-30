package bg.softuni.restaurants_management.model.dto;

import java.time.LocalDate;

public class CreateAllReservationsDateBindingModel {
    private String datepicker;

    private Long restaurantId;

    public Long getRestaurantId() {
        return restaurantId;
    }

    public CreateAllReservationsDateBindingModel setRestaurantId(Long restaurantId) {
        this.restaurantId = restaurantId;
        return this;
    }

    public String getDatepicker() {
        return datepicker;
    }

    public CreateAllReservationsDateBindingModel setDatepicker(String datepicker) {
        this.datepicker = datepicker;
        return this;
    }
}
