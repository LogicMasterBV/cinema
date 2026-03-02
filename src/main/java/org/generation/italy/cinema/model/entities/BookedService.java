package org.generation.italy.cinema.model.entities;


import jakarta.persistence.*;

@Entity
@Table(name = "booking_service", schema = "public")
public class BookedService {

    @EmbeddedId
    private BookedServiceId id = new BookedServiceId();

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @MapsId("bookingId")
    @JoinColumn(name = "id_booking", nullable = false)
    private Booking booking;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @MapsId("serviceId")
    @JoinColumn(name = "id_service", nullable = false)
    private ExtraProduct service;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    public BookedService() {}

    // getters/setters
    public BookedServiceId getId() { return id; }
    public void setId(BookedServiceId id) { this.id = id; }

    public Booking getBooking() { return booking; }
    public void setBooking(Booking booking) { this.booking = booking; }

    public ExtraProduct getService() {
        return service;
    }

    public void setService(ExtraProduct service) {
        this.service = service;
    }

    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }
}