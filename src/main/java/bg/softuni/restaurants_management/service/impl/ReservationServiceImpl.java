package bg.softuni.restaurants_management.service.impl;

import bg.softuni.restaurants_management.model.dto.CreateAllReservationsDateBindingModel;
import bg.softuni.restaurants_management.model.dto.ReservationViewModel;
import bg.softuni.restaurants_management.model.entity.Reservation;
import bg.softuni.restaurants_management.model.entity.Restaurant;
import bg.softuni.restaurants_management.model.entity.TableEntity;
import bg.softuni.restaurants_management.model.entity.UserEntity;
import bg.softuni.restaurants_management.model.enums.ReservationEnums;
import bg.softuni.restaurants_management.repository.ReservationRepository;
import bg.softuni.restaurants_management.repository.RestaurantRepository;
import bg.softuni.restaurants_management.service.ReservationService;
import bg.softuni.restaurants_management.service.UserService;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class ReservationServiceImpl implements ReservationService {
    private final ReservationRepository reservationRepository;
    private final RestaurantRepository restaurantRepository;
    private final UserService userService;
    private final ModelMapper modelMapper;

    public ReservationServiceImpl(ReservationRepository reservationRepository, RestaurantRepository restaurantRepository, UserService userService, ModelMapper modelMapper) {
        this.reservationRepository = reservationRepository;
        this.restaurantRepository = restaurantRepository;
        this.userService = userService;
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
                    .toList();

            reservationRepository.saveAll(list);
        }
    }

    @Override
    public List<ReservationViewModel> findAllByDateIsAndUser_IdAndTable_Restaurant_Id(Long restaurantId, Long userId, String datepicker) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        LocalDate localDate = LocalDate.parse(datepicker, formatter);
        List<Reservation> allByDateIsAndTableRestaurantId =
                reservationRepository.findAllByDateIsAndUser_IdAndTable_Restaurant_Id(localDate, userId, restaurantId);
        return allByDateIsAndTableRestaurantId.stream()
                .map(view -> modelMapper.map(view, ReservationViewModel.class))
                .toList();
    }

    @Override
    @Transactional
    public void makeReservation(Long reservationId, String userEmail) {
        Optional<Reservation> reservation = reservationRepository.findById(reservationId);
        Optional<UserEntity> userByEmail = userService.findUserByEmail(userEmail);

        if (reservation.isPresent() && userByEmail.isPresent()) {
            Reservation updatedReservation = reservation.get().setUser(userByEmail.get());
            reservationRepository.save(updatedReservation);
        }

    }

    @Override
    public int createReservationForActiveRestaurantsForDate(LocalDate date) {

        List<Restaurant> allActiveRestaurants = restaurantRepository.findAllByIsActiveIs(true);
        List<ReservationEnums> values = Arrays.stream(ReservationEnums.values()).toList();
        List<Reservation> collectionOfReservations =
                createListOfReservations(allActiveRestaurants, date, values);

        List<Reservation> alreadyCreatedReservations = allActiveRestaurants.stream()
                .map(restaurant -> {
                    return reservationRepository.findAllByDateIsAndTable_Restaurant_Id(date, restaurant.getId());
                }).toList()
                .stream().flatMap(List::stream)
                .toList();
        ;

        List<Reservation> reservationsToSave = collectionOfReservations.stream()
                .filter(x -> !alreadyCreatedReservations.contains(x))
                .toList();

        reservationRepository.saveAll(reservationsToSave);
        return reservationsToSave.size();
    }

    private List<Reservation> checkForReservation(LocalDate date) {
        return reservationRepository.findAllByDateIs(date).orElse(new ArrayList<>());
    }

    private List<Reservation> createListOfReservations(List<Restaurant> allActiveRestaurants,
                                                       LocalDate date,
                                                       List<ReservationEnums> values) {
        return allActiveRestaurants.stream()
                .map(Restaurant::getTableEntities)
                .flatMap(List::stream)
                .map(table -> {
                    return values.stream()
                            .map(reservationEnums -> {
                                return new Reservation().setDate(date).setTable(table).setReservations(reservationEnums);

                            }).toList();
                }).toList()
                .stream().flatMap(List::stream)
                .toList();
    }
}
