package org.generation.italy.cinema.dto;

import org.generation.italy.cinema.model.entities.Screening;

import java.time.LocalDate;
import java.time.LocalTime;

public class ScreeningDTO {
    private Integer id;
    private Integer filmId;
    private Integer hallId;
    private LocalDate screeningDate;
    private LocalTime screeningTime;

    // Costruttore vuoto (necessario per la deserializzazione JSON)
    public ScreeningDTO() {}

    // Costruttore per convertire da entità a DTO (output)
    public ScreeningDTO(Screening screening) {
        this.id = screening.getId();
        this.filmId = screening.getFilm().getId();
        this.hallId = screening.getHall().getId();
        this.screeningDate = screening.getScreeningDate();
        this.screeningTime = screening.getScreeningTime();
    }

    public Integer getId() {return id;}
    public void setId(Integer id) {this.id = id;}
    public Integer getFilmId() {return filmId;}
    public void setFilmId(Integer filmId) {this.filmId = filmId;}
    public Integer getHallId() {return hallId;}
    public void setHallId(Integer hallId) {this.hallId = hallId;}
    public LocalDate getScreeningDate() {return screeningDate;}
    public void setScreeningDate(LocalDate screeningDate) {this.screeningDate = screeningDate;}
    public LocalTime getScreeningTime() {return screeningTime;}
    public void setScreeningTime(LocalTime screeningTime) {this.screeningTime = screeningTime;}
}
