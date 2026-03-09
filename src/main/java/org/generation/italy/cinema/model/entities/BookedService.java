package org.generation.italy.cinema.model.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
@Table(name = "booking_service", schema = "public")
public class BookedService {

    @EmbeddedId
    @JsonIgnore
    private BookedServiceId id = new BookedServiceId();

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @MapsId("bookingId")
    @JoinColumn(name = "id_booking", nullable = false)
    @JsonIgnore
    private Booking booking;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @MapsId("serviceId")
    @JoinColumn(name = "id_service", nullable = false)
    private CinemaService service;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    public BookedService() {}

    // getters/setters
    public BookedServiceId getId() { return id; }
    public void setId(BookedServiceId id) { this.id = id; }

    public Booking getBooking() { return booking; }
    public void setBooking(Booking booking) { this.booking = booking; }

    public CinemaService getService() {
        return service;
    }

    public void setService(CinemaService service) {
        this.service = service;
    }

    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }
}