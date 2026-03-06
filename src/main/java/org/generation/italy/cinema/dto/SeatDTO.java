package org.generation.italy.cinema.dto;

import org.generation.italy.cinema.model.entities.Seat;

public class SeatDTO {
    private int id;
    private int rowNumber;
    private int seatNumber;
    private HallDTO hall;

    public SeatDTO(Seat seat) {
        this.id = seat.getId();
        this.rowNumber = seat.getRowNumber();
        this.seatNumber = seat.getSeatNumber();
        this.hall = new HallDTO(seat.getHall());
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRowNumber() {
        return rowNumber;
    }

    public void setRowNumber(int rowNumber) {
        this.rowNumber = rowNumber;
    }

    public int getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(int seatNumber) {
        this.seatNumber = seatNumber;
    }

    public HallDTO getHall() {
        return hall;
    }

    public void setHall(HallDTO hall) {
        this.hall = hall;
    }
}
