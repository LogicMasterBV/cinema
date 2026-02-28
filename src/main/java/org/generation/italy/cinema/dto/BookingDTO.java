package org.generation.italy.cinema.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class BookingDTO {

    private Integer id;
    private Integer userId;
    private Integer screeningId;
    private BigDecimal totalPrice;
    private LocalDateTime bookingDate;
    // Non ho messo oggetto User, Screening e lista ticket - BookingService cosi evitiamo lazy problem/lazy JSON - oggetti troppo pesanti
    public BookingDTO() {
    }

    public BookingDTO(Integer id,
                      Integer userId,
                      Integer screeningId,
                      BigDecimal totalPrice,
                      LocalDateTime bookingDate) {
        this.id = id;
        this.userId = userId;
        this.screeningId = screeningId;
        this.totalPrice = totalPrice;
        this.bookingDate = bookingDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getScreeningId() {
        return screeningId;
    }

    public void setScreeningId(Integer screeningId) {
        this.screeningId = screeningId;
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
}
