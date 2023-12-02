package bg.softuni.restaurants_management.model.entity;

import bg.softuni.restaurants_management.model.enums.ReservationEnums;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reservation that = (Reservation) o;
        return reservations == that.reservations && Objects.equals(date, that.date) && Objects.equals(table.getId(), that.table.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(reservations, date, table);
    }
}
