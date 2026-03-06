package org.generation.italy.cinema.dto;

import org.generation.italy.cinema.model.entities.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class FilmDTO {
    private Integer id;
    private String title;
    private Integer durationMinutes;
    private String description;
    private LocalDate releaseDate;
    private Integer ageRating;
    private DirectorDTO director;
    private List<ScreeningDTO> screenings;
    private Set<ActorDTO> actors;
    private Set<GenreDTO> genres;
    private String imagePath;

    public FilmDTO(){}

    public FilmDTO(Integer id, String title, Integer durationMinutes, String description, LocalDate releaseDate,
                   Integer ageRating, DirectorDTO director, List<ScreeningDTO> screenings, Set<ActorDTO> actors, Set<GenreDTO> genres, String imagePath) {
        this.id = id;
        this.title = title;
        this.durationMinutes = durationMinutes;
        this.description = description;
        this.releaseDate = releaseDate;
        this.ageRating = ageRating;
        this.director = director;
        this.screenings = screenings;
        this.actors = actors;
        this.genres = genres;
        this.imagePath = imagePath;
    }
    //entity -> dto
    public static FilmDTO fromEntity(Film film){
        if(film == null){
            return null;
        }
        return new FilmDTO(
                film.getId(),
                film.getTitle(),
                film.getDurationMinutes(),
                film.getDescription(),
                film.getReleaseDate(),
                film.getAgeRating(),
                film.getDirector() != null ? DirectorDTO.fromDirector(film.getDirector()) : null,
                film.getScreenings() != null ? film.getScreenings().stream().map(ScreeningDTO::new).toList() : null,
                film.getActors() != null ? film.getActors().stream().map(ActorDTO::fromEntity).collect(Collectors.toSet()): null,
                film.getGenres() != null ? film.getGenres().stream().map(GenreDTO::fromEntity).collect(Collectors.toSet()) : null,
                film.getImagePath()
        );
    }

    public Film toEntity() {
        Film film = new Film();

        film.setId(this.id);
        film.setTitle(this.title);
        film.setDurationMinutes(this.durationMinutes);
        film.setDescription(this.description);
        film.setReleaseDate(this.releaseDate);
        film.setAgeRating(this.ageRating);
        film.setImagePath(this.imagePath);

        // director
        if (this.director != null) {
            Director d = new Director();
            d.setId(this.director.getId());
            film.setDirector(d);
        }

        // screenings
        if (this.screenings != null) {
            film.setScreenings(
                    this.screenings.stream()
                            .map(dto -> {
                                Screening s = new Screening();
                                s.setId(dto.getId());
                                return s;
                            })
                            .toList()
            );
        }

        // actors
        if (this.actors != null) {
            film.setActors(
                    this.actors.stream()
                            .map(dto -> {
                                Actor a = new Actor();
                                a.setId(dto.getId());
                                return a;
                            })
                            .collect(Collectors.toSet())
            );
        }

        // genres
        if (this.genres != null) {
            film.setGenres(
                    this.genres.stream()
                            .map(dto -> {
                                Genre g = new Genre();
                                g.setId(dto.getId());
                                return g;
                            })
                            .collect(Collectors.toSet())
            );
        }

        return film;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getDurationMinutes() {
        return durationMinutes;
    }

    public void setDurationMinutes(Integer durationMinutes) {
        this.durationMinutes = durationMinutes;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    public Integer getAgeRating() {
        return ageRating;
    }

    public void setAgeRating(Integer ageRating) {
        this.ageRating = ageRating;
    }

    public DirectorDTO getDirector() {
        return director;
    }

    public void setDirector(DirectorDTO director) {
        this.director = director;
    }

    public List<ScreeningDTO> getScreenings() {
        return screenings;
    }

    public void setScreenings(List<ScreeningDTO> screenings) {
        this.screenings = screenings;
    }

    public Set<ActorDTO> getActors() {
        return actors;
    }

    public void setActors(Set<ActorDTO> actors) {
        this.actors = actors;
    }

    public Set<GenreDTO> getGenres() {
        return genres;
    }

    public void setGenres(Set<GenreDTO> genres) {
        this.genres = genres;
    }
}
