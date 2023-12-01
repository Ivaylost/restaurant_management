package bg.softuni.restaurants_management.model.dto;

import bg.softuni.restaurants_management.model.entity.Reservation;
import bg.softuni.restaurants_management.model.entity.TableEntity;
import jakarta.persistence.Table;

public class ReservationViewModel {
    private Long reservationId;
    private String tableName;
    private String restaurantName;
    private String reservationName;

    public Long getReservationId() {
        return reservationId;
    }

    public ReservationViewModel setReservationId(Long reservationId) {
        this.reservationId = reservationId;
        return this;
    }

    public String getTableName() {
        return tableName;
    }

    public ReservationViewModel setTableName(String tableName) {
        this.tableName = tableName;
        return this;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public ReservationViewModel setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
        return this;
    }

    public String getReservationName() {
        return reservationName;
    }

    public ReservationViewModel setReservationName(String reservationName) {
        this.reservationName = reservationName;
        return this;
    }
}
