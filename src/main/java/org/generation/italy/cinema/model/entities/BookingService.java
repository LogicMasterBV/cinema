package org.generation.italy.cinema.model.entities;


import jakarta.persistence.*;

@Entity
@Table(name = "booking_service", schema = "public")
public class BookingService {

    @EmbeddedId
    private BookingServiceId id = new BookingServiceId();

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @MapsId("bookingId")
    @JoinColumn(name = "id_booking", nullable = false)
    private Booking booking;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @MapsId("serviceId")
    @JoinColumn(name = "id_service", nullable = false)
    private Service service;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    public BookingService() {}

    // getters/setters
    public BookingServiceId getId() { return id; }
    public void setId(BookingServiceId id) { this.id = id; }

    public Booking getBooking() { return booking; }
    public void setBooking(Booking booking) { this.booking = booking; }

    public Service getService() { return service; }
    public void setService(Service service) { this.service = service; }

    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }
}