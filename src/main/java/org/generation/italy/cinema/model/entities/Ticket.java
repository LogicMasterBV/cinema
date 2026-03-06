package org.generation.italy.cinema.model.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(
        name = "ticket",
        schema = "public",
        uniqueConstraints = @UniqueConstraint(
                name = "uk_ticket",
                columnNames = {"id_screening", "id_seat"}
        )
)
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_ticket")
    private Integer id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY) // JPA aggiorna la relazione solo dal lato owner! Booking = padre, Ticket = figlio, Chi decide chi è il padre? Il documento del figlio.
    @JoinColumn(name = "id_booking", nullable = false) // -------------------------BIDIREZIONALE CON BOOKING -> Lato owner verso Booking, ecco perchè devo gestire i biglietti in booking
    @JsonIgnore
    private Booking booking;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "id_screening", nullable = false)
    @JsonIgnore
    private Screening screening;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "id_seat", nullable = false)
    @JsonIgnore
    private Seat seat;

    @Column(name = "price", precision = 8, scale = 2, nullable = false)
    private BigDecimal price;

    @Column(name = "date", insertable = false, updatable = false, nullable = false)
    private LocalDateTime date;

    public Ticket() {}

    // getters/setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public Booking getBooking() { return booking; }
    public void setBooking(Booking booking) { this.booking = booking; }

    public Screening getScreening() { return screening; }
    public void setScreening(Screening screening) { this.screening = screening; }

    public Seat getSeat() { return seat; }
    public void setSeat(Seat seat) { this.seat = seat; }

    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }

    public LocalDateTime getDate() { return date; }
    public void setDate(LocalDateTime date) { this.date = date; }
}