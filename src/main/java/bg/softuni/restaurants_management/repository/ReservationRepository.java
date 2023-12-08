package bg.softuni.restaurants_management.repository;

import bg.softuni.restaurants_management.model.entity.Reservation;
import bg.softuni.restaurants_management.model.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    Optional<List<Reservation>> findAllByDateIs(LocalDate date);

    //    List<Reservation> findAllByTable_Restaurant_IdAndTable(Long restaurantId, LocalDate date);
    List<Reservation> findAllByDateIsAndUser_IdAndTable_Restaurant_Id(LocalDate date, Long userId, Long restaurantId);

    List<Reservation> findAllByDateIsAndTable_Restaurant_Id(LocalDate date, Long restaurantId);
    List<Reservation> findAllByUser(UserEntity user);
}
