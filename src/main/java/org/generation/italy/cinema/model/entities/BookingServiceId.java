package org.generation.italy.cinema.model.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class BookingServiceId implements Serializable {

    @Column(name = "id_booking")
    private Integer bookingId;

    @Column(name = "id_service")
    private Integer serviceId;

    public BookingServiceId() {}

    public BookingServiceId(Integer bookingId, Integer serviceId) {
        this.bookingId = bookingId;
        this.serviceId = serviceId;
    }

    public Integer getBookingId() { return bookingId; }
    public void setBookingId(Integer bookingId) { this.bookingId = bookingId; }

    public Integer getServiceId() { return serviceId; }
    public void setServiceId(Integer serviceId) { this.serviceId = serviceId; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BookingServiceId that)) return false;
        return Objects.equals(bookingId, that.bookingId) && Objects.equals(serviceId, that.serviceId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bookingId, serviceId);
    }
}