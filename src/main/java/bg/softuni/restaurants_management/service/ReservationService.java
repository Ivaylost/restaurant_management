package bg.softuni.restaurants_management.service;

import bg.softuni.restaurants_management.model.dto.CreateAllReservationsDateBindingModel;
import bg.softuni.restaurants_management.model.dto.ReservationViewModel;
import bg.softuni.restaurants_management.model.entity.Reservation;
import bg.softuni.restaurants_management.model.entity.UserEntity;

import java.time.LocalDate;
import java.util.List;

public interface ReservationService {
    void initReservationByRestaurant(CreateAllReservationsDateBindingModel createAllReservationsDateBindingModel);

    List<ReservationViewModel> findAllByDateIsAndUser_IdAndTable_Restaurant_Id(Long restaurantId, Long userId, String datepicker);

    void makeReservation(Long reservationId, String userEmail);

    int createReservationForActiveRestaurantsForDate(LocalDate date);

    List<ReservationViewModel> getMyReservations(UserEntity loggedUser);
}
