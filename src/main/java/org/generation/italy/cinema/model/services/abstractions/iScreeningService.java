package org.generation.italy.cinema.model.services.abstractions;

import org.generation.italy.cinema.model.entities.Screening;
import org.generation.italy.cinema.model.entities.Seat;

import java.time.LocalDate;
import java.util.List;

public interface iScreeningService {
    List<Screening> getAll();
    Screening getById(Integer id);
    List<Screening> getByFilm(Integer filmId);
    List<Screening> getByDate(LocalDate date);
    List<Screening> getByDateBetween(LocalDate from, LocalDate to);
    List<Screening> getByHall(Integer hallId);
    List<Screening> getByHallAndDate(Integer hallId, LocalDate date);
    List<Seat> getAvailableSeats(Integer screeningId);
    long getOccupiedSeats(Integer screeningId);
    Screening create(Screening screen);
    Screening update(Integer id, Screening screen);
    void delete(Integer id);
}
