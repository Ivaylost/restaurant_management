package bg.softuni.restaurants_management.model.dto;

public class ReservationViewModel {
    private Long reservationId;
    private String tableName;
    private String restaurantName;
    private String reservationName;
    private String date;

    private Long userId;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getDate() {
        return date;
    }

    public ReservationViewModel setDate(String date) {
        this.date = date;
        return this;
    }

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
