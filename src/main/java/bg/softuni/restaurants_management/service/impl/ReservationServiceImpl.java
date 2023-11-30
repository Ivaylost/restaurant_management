package bg.softuni.restaurants_management.service.impl;

import bg.softuni.restaurants_management.model.dto.CreateAllReservationsDateBindingModel;
import bg.softuni.restaurants_management.model.entity.Reservation;
import bg.softuni.restaurants_management.model.entity.Restaurant;
import bg.softuni.restaurants_management.model.entity.TableEntity;
import bg.softuni.restaurants_management.model.enums.ReservationEnums;
import bg.softuni.restaurants_management.repository.ReservationRepository;
import bg.softuni.restaurants_management.repository.RestaurantRepository;
import bg.softuni.restaurants_management.service.ReservationService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ReservationServiceImpl implements ReservationService {
    private final ReservationRepository reservationRepository;
    private final RestaurantRepository restaurantRepository;
    private final ModelMapper modelMapper;

    public ReservationServiceImpl(ReservationRepository reservationRepository, RestaurantRepository restaurantRepository, ModelMapper modelMapper) {
        this.reservationRepository = reservationRepository;
        this.restaurantRepository = restaurantRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public void initReservationByRestaurant(CreateAllReservationsDateBindingModel createAllReservationsDateBindingModel) {
        List<TableEntity> tables = null;
        Optional<Restaurant> restaurant = restaurantRepository.findById(createAllReservationsDateBindingModel.getRestaurantId());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        LocalDate localDate = LocalDate.parse(createAllReservationsDateBindingModel.getDatepicker(), formatter);

        if (restaurant.isPresent() && checkForReservation(localDate).isEmpty()) {
            tables = restaurant.get().getTableEntities();

            List<ReservationEnums> values = Arrays.stream(ReservationEnums.values()).toList();

            List<Reservation> list = tables.stream()
                    .map(table -> {
                        return values.stream()
                                .map(reservationEnums -> {
                                    return new Reservation().setDate(localDate).setTable(table).setReservations(reservationEnums);
                                }).toList();
                    }).toList()
                    .stream().flatMap(List::stream)
                    .collect(Collectors.toList());

            reservationRepository.saveAll(list);
        }
    }

    private List<Reservation> checkForReservation(LocalDate date) {
        return reservationRepository.findAllByDateIs(date).orElse(new ArrayList<>());
    }
}
