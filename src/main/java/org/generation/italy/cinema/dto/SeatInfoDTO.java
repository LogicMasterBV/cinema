package org.generation.italy.cinema.dto;

public class SeatInfoDTO {

    private Integer seatId;
    private Integer row;
    private Integer number;

    public SeatInfoDTO(Integer seatId, Integer row, Integer number) {
        this.seatId = seatId;
        this.row = row;
        this.number = number;
    }

    public Integer getSeatId() { return seatId; }
    public Integer getRow() { return row; }
    public Integer getNumber() { return number; }
}