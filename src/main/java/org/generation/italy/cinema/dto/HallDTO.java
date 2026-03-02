package org.generation.italy.cinema.dto;

import org.generation.italy.cinema.model.entities.Hall;

public class HallDTO {

    private Integer id;
    private String name;
    private Integer capacity;

    public HallDTO() {}

    public static HallDTO fromEntity(Hall hall) {
        if (hall == null) return null;

        HallDTO dto = new HallDTO();
        dto.setId(hall.getId());
        dto.setName(hall.getName());
        dto.setCapacity(hall.getCapacity());
        return dto;
    }

    public static Hall toEntity(HallDTO dto) {
        if (dto == null) return null;

        Hall hall = new Hall();
        hall.setId(dto.getId());
        hall.setName(dto.getName());
        hall.setCapacity(dto.getCapacity());
        return hall;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }
}