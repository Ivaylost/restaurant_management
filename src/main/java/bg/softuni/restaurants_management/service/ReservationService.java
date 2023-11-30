package bg.softuni.restaurants_management.service;

import bg.softuni.restaurants_management.model.dto.CreateAllReservationsDateBindingModel;
import bg.softuni.restaurants_management.model.entity.Reservation;

import java.util.List;

public interface ReservationService {
    void initReservationByRestaurant(CreateAllReservationsDateBindingModel createAllReservationsDateBindingModel);

    List<Reservation> findAllByDateIsAndUser_IdAndTable_Restaurant_Id(Long restaurantId, Long userId, String datepicker);
}
