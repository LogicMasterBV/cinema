package org.generation.italy.cinema.dto;

import org.generation.italy.cinema.model.entities.Actor;
import org.generation.italy.cinema.model.entities.Director;
import org.generation.italy.cinema.model.entities.Genre;
import org.generation.italy.cinema.model.entities.Screening;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FilmDTO {
    private Integer id;
    private String title;
    private Integer durationMinutes;
    private String description;
    private LocalDate releaseDate;
    private Integer ageRating;
    private String directorName;
    private List<String> screenings;
    private Set<String> actors;
    private Set<String> genres;


}
