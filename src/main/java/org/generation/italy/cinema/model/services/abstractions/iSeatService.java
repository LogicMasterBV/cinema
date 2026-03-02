package org.generation.italy.cinema.model.services.abstractions;

import org.generation.italy.cinema.model.entities.Seat;

import java.util.List;

public interface iSeatService {
    List<Seat> getAll();
    Seat getById(int id);
    List<Seat> getByHallId(int hallId);
    Seat create(Seat seat);
    Seat update(int id, Seat seat);
    boolean delete(int id);
}