package org.generation.italy.cinema.model.entities;


import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "booking", schema = "public")
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_booking")
    private Integer id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "id_user", nullable = false)
    private User user;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "id_screening", nullable = false)
    private Screening screening;

    @Column(name = "total_price", precision = 10, scale = 2, nullable = false)
    private BigDecimal totalPrice;

    @Column(name = "booking_date", nullable = false, updatable = false)
    private LocalDateTime bookingDate;

    @OneToMany(mappedBy = "booking", // @OneToMany (mappedby ...) -> significa "io non gestisco la relazione, la gestisce l'altro lato". Booking non scrive nel DB, sarà ticket a scriverci --> // --------------------BIDIREZIONALE CON TICKET
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<Ticket> tickets = new ArrayList<>();

    @OneToMany(mappedBy = "booking",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<BookedService> bookedServices = new ArrayList<>();

    public Booking() {
    }

    // ===== SINCRONIZZAZIONE RELAZIONE =====

    // Metodo necessario per sincronizzare entrambi i lati della relazione bidirezionale,
    // perché il lato owner (Ticket) è quello che gestisce la foreign key nel database.
    public void addTicket(Ticket ticket) {
        tickets.add(ticket);
        ticket.setBooking(this);
    }

    public void removeTicket(Ticket ticket) {
        tickets.remove(ticket);
        ticket.setBooking(null);
    }

    public void addBookingService(BookedService service) {
        bookedServices.add(service);
        service.setBooking(this);
    }

    public void removeBookingService(BookedService service) {
        bookedServices.remove(service);
        service.setBooking(null);
    }

    @PrePersist
    public void prePersist() {
        bookingDate = LocalDateTime.now();
    }

    // getters/setters normali

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Screening getScreening() {
        return screening;
    }

    public void setScreening(Screening screening) {
        this.screening = screening;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public LocalDateTime getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(LocalDateTime bookingDate) {
        this.bookingDate = bookingDate;
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(List<Ticket> tickets) {
        this.tickets = tickets;
    }

    public List<BookedService> getBookingServices() {
        return bookedServices;
    }

    public void setBookingServices(List<BookedService> bookedServices) {
        this.bookedServices = bookedServices;
    }
}

