package org.generation.italy.cinema.dto;

public class BookedServiceDTO {
// Visto che è una tabella di collegamento con chiave composta il DTO deve contenere bookingId, ExtraProductId (vecchio serviceId) e la quantity
private Integer bookingId;
    private Integer extraProductId;
    private Integer quantity;

    public BookedServiceDTO() {}

    public Integer getBookingId() {
        return bookingId;
    }

    public void setBookingId(Integer bookingId) {
        this.bookingId = bookingId;
    }

    public Integer getExtraProductId() {
        return extraProductId;
    }

    public void setExtraProductId(Integer extraProductId) {
        this.extraProductId = extraProductId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
