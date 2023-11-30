package bg.softuni.restaurants_management.model.entity;

import bg.softuni.restaurants_management.model.enums.ReservationEnums;
import jakarta.persistence.*;
@Entity
@Table(name = "reservationSlot")
public class ReservationSlot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(unique = true, nullable = false)
    private ReservationEnums reservationSlot;

    public Long getId() {
        return id;
    }

    public ReservationSlot setId(Long id) {
        this.id = id;
        return this;
    }

    public ReservationEnums getReservationSlot() {
        return reservationSlot;
    }

    public ReservationSlot setReservationSlot(ReservationEnums reservationSlot) {
        this.reservationSlot = reservationSlot;
        return this;
    }
}
