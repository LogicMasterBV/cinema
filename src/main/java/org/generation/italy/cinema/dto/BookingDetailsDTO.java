package org.generation.italy.cinema.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class BookingDetailsDTO {

    private Integer bookingId;
    private String filmTitle;
    private LocalDate screeningDate;
    private LocalTime screeningTime;
    private BigDecimal totalPrice;
    private List<SeatInfoDTO> seats;

    public BookingDetailsDTO(Integer bookingId,
                             String filmTitle,
                             LocalDate screeningDate,
                             LocalTime screeningTime,
                             BigDecimal totalPrice,
                             List<SeatInfoDTO> seats) {
        this.bookingId = bookingId;
        this.filmTitle = filmTitle;
        this.screeningDate = screeningDate;
        this.screeningTime = screeningTime;
        this.totalPrice = totalPrice;
        this.seats = seats;
    }

    public Integer getBookingId() { return bookingId; }
    public String getFilmTitle() { return filmTitle; }
    public LocalDate getScreeningDate() { return screeningDate; }
    public LocalTime getScreeningTime() { return screeningTime; }
    public BigDecimal getTotalPrice() { return totalPrice; }
    public List<SeatInfoDTO> getSeats() { return seats; }
}