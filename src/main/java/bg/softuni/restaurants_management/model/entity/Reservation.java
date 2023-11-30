package bg.softuni.restaurants_management.model.entity;

import bg.softuni.restaurants_management.model.enums.ReservationEnums;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "reservations")
public class Reservation extends BaseEntity{

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ReservationEnums reservations;

    private LocalDate date;

    @ManyToOne
    private TableEntity table;

    @ManyToOne
    private UserEntity user;

    public ReservationEnums getReservations() {
        return reservations;
    }

    public Reservation setReservations(ReservationEnums reservations) {
        this.reservations = reservations;
        return this;
    }

    public LocalDate getDate() {
        return date;
    }

    public Reservation setDate(LocalDate date) {
        this.date = date;
        return this;
    }

    public TableEntity getTable() {
        return table;
    }

    public Reservation setTable(TableEntity table) {
        this.table = table;
        return this;
    }

    public UserEntity getUser() {
        return user;
    }

    public Reservation setUser(UserEntity user) {
        this.user = user;
        return this;
    }
}
