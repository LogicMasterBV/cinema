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
    private Integer directorId;
    private List<Integer> screeningsId;
    private Set<Integer> actorsId;
    private Set<Integer> genresId;

    public FilmDTO(){}

    public FilmDTO(Integer id, String title, Integer durationMinutes, String description, LocalDate releaseDate,
                   Integer ageRating, Integer directorId, List<Integer> screeningsId, Set<Integer> actorsId, Set<Integer> genresId) {
        this.id = id;
        this.title = title;
        this.durationMinutes = durationMinutes;
        this.description = description;
        this.releaseDate = releaseDate;
        this.ageRating = ageRating;
        this.directorId = directorId;
        this.screeningsId = screeningsId;
        this.actorsId = actorsId;
        this.genresId = genresId;
    }
    //entity -> dto
    public static FilmDTO fromEntity(Film film){
        if(film == null){
            return null;
        }
        return new FilmDTO(film.getId(), film.getTitle(),film.getDurationMinutes(),film.getDescription(),
                film.getReleaseDate(), film.getAgeRating(), film.getDirector() != null ? film.getDirector().getId() : null,
                film.getScreenings() != null ? film.getScreenings().stream().map(Screening::getId).toList() : null,
                film.getActors() != null ? film.getActors().stream().map(Actor::getId).collect(Collectors.toSet()): null,
                film.getGenres() != null ? film.getGenres().stream().map(Genre::getId).collect(Collectors.toSet()) : null);
    }
    //dto -> entity
//    public Film toEntity(FilmDTO dto){
//        return new Film(id, title, durationMinutes, description, releaseDate, ageRating,
//                directorId, screeningsId, actorsId, genresId);
//    }

    public Film toEntity() {
        Film film = new Film();

        film.setId(this.id);
        film.setTitle(this.title);
        film.setDurationMinutes(this.durationMinutes);
        film.setDescription(this.description);
        film.setReleaseDate(this.releaseDate);
        film.setAgeRating(this.ageRating);

        // director
        if (this.directorId != null) {
            Director d = new Director();
            d.setId(this.directorId);
            film.setDirector(d);
        }

        // screenings
        if (this.screeningsId != null) {
            film.setScreenings(
                    this.screeningsId.stream()
                            .map(id -> {
                                Screening s = new Screening();
                                s.setId(id);
                                return s;
                            })
                            .toList()
            );
        }

        // actors
        if (this.actorsId != null) {
            film.setActors(
                    this.actorsId.stream()
                            .map(id -> {
                                Actor a = new Actor();
                                a.setId(id);
                                return a;
                            })
                            .collect(Collectors.toSet())
            );
        }

        // genres
        if (this.genresId != null) {
            film.setGenres(
                    this.genresId.stream()
                            .map(id -> {
                                Genre g = new Genre();
                                g.setId(id);
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

    public Integer getDirectorId() {
        return directorId;
    }

    public void setDirectorId(Integer directorId) {
        this.directorId = directorId;
    }

    public List<Integer> getScreeningsId() {
        return screeningsId;
    }

    public void setScreeningsId(List<Integer> screeningsId) {
        this.screeningsId = screeningsId;
    }

    public Set<Integer> getActorsId() {
        return actorsId;
    }

    public void setActorsId(Set<Integer> actorsId) {
        this.actorsId = actorsId;
    }

    public Set<Integer> getGenresId() {
        return genresId;
    }

    public void setGenresId(Set<Integer> genresId) {
        this.genresId = genresId;
    }
}
