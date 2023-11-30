package bg.softuni.restaurants_management.service;

import bg.softuni.restaurants_management.model.dto.CreateAllReservationsDateBindingModel;

public interface ReservationService {
    void initReservationByRestaurant(CreateAllReservationsDateBindingModel createAllReservationsDateBindingModel);
}
