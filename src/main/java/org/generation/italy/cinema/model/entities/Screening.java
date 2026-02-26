package org.generation.italy.cinema.model.entities;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(
        name = "screening",
        schema = "public",
        uniqueConstraints = @UniqueConstraint(
                name = "uk_screening",
                columnNames = {"id_hall", "screening_date", "screening_time"}
        )
)
public class Screening {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_screening")
    private Integer id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "id_hall", nullable = false)
    private Hall hall;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "id_film", nullable = false)
    private Film film;

    @Column(name = "screening_date", nullable = false)
    private LocalDate screeningDate;

    @Column(name = "screening_time", nullable = false)
    private LocalTime screeningTime;

    @Column(name = "base_price", precision = 8, scale = 2, nullable = false)
    private BigDecimal basePrice;

    @OneToMany(mappedBy = "screening")
    private List<Booking> bookings = new ArrayList<>();

    @OneToMany(mappedBy = "screening")
    private List<Ticket> tickets = new ArrayList<>();

    public Screening() {}

    // getters/setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public Hall getHall() { return hall; }
    public void setHall(Hall hall) { this.hall = hall; }

    public Film getFilm() { return film; }
    public void setFilm(Film film) { this.film = film; }

    public LocalDate getScreeningDate() { return screeningDate; }
    public void setScreeningDate(LocalDate screeningDate) { this.screeningDate = screeningDate; }

    public LocalTime getScreeningTime() { return screeningTime; }
    public void setScreeningTime(LocalTime screeningTime) { this.screeningTime = screeningTime; }

    public BigDecimal getBasePrice() { return basePrice; }
    public void setBasePrice(BigDecimal basePrice) { this.basePrice = basePrice; }

    public List<Booking> getBookings() { return bookings; }
    public void setBookings(List<Booking> bookings) { this.bookings = bookings; }

    public List<Ticket> getTickets() { return tickets; }
    public void setTickets(List<Ticket> tickets) { this.tickets = tickets; }
}
