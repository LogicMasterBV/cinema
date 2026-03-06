package org.generation.italy.cinema.dto;

import org.generation.italy.cinema.model.entities.Genre;

public class GenreDTO {
    private Integer id;
    private String name;

    public GenreDTO() {}

    public GenreDTO(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public static GenreDTO fromEntity(Genre genre) {
        if (genre == null) return null;
        return new GenreDTO(genre.getId(), genre.getName());
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
}
