package org.generation.italy.cinema.dto;

import org.generation.italy.cinema.model.entities.Hall;

public class HallDTO {
    private int id;
    private String name;
    private int capacity;

    public HallDTO(Hall hall) {
        this.id = hall.getId();
        this.name = hall.getName();
        this.capacity = hall.getCapacity();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCapacity() {
        return capacity;
    }

    public static Hall toEntity(HallDTO dto) {
        Hall hall = new Hall();
        hall.setId(dto.getId());
        hall.setName(dto.getName());
        hall.setCapacity(dto.getCapacity());
        return hall;
    }

    public static HallDTO fromEntity(Hall hall) {
        return new HallDTO(hall);
    }
    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }
}
