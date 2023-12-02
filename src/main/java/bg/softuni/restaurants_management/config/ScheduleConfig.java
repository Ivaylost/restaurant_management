package bg.softuni.restaurants_management.config;

import bg.softuni.restaurants_management.service.ReservationService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class ScheduleConfig {
    private final ReservationService reservationService;

    public ScheduleConfig(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

//    @Scheduled(cron = "0 * * * * *") for executing every minute
@Scheduled(cron = "0 0 0 * * *",zone = "UTC")
    public void executeAtMidnight() {

        long dateOfCreationOfNewReservations = 5L;
        LocalDate date = LocalDate.now().plusDays(dateOfCreationOfNewReservations);

        int nums = reservationService.createReservationForActiveRestaurantsForDate(date);

        System.out.printf("%d reservation added!", nums);
    }
}
