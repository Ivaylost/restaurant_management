package bg.softuni.restaurants_management.repository;

import bg.softuni.restaurants_management.model.entity.ReservationSlot;
import bg.softuni.restaurants_management.model.enums.ReservationEnums;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationSlotRepository extends JpaRepository<ReservationSlot, Long> {
    ReservationSlot findByReservationSlot(ReservationEnums reservationEnums);
}
