package org.generation.italy.cinema.model.services.implementations;

import jakarta.transaction.Transactional;
import org.generation.italy.cinema.model.entities.Hall;
import org.generation.italy.cinema.model.entities.Seat;
import org.generation.italy.cinema.model.repositories.abstractions.HallRepository;
import org.generation.italy.cinema.model.repositories.abstractions.SeatRepository;
import org.generation.italy.cinema.model.services.abstractions.iHallService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HallServiceJpa implements iHallService {
    private final HallRepository repo;
    private final SeatRepository seatRepo;

    public HallServiceJpa(HallRepository repo, SeatRepository seatRepo) {
        this.repo = repo;
        this.seatRepo = seatRepo;
    }

    @Override
    public List<Hall> findHallAll() {
        return repo.findAll();
    }

    @Override
    public Optional<Hall> findHallById(Integer id) {
        return repo.findById(id);
    }

    @Override
    @Transactional
    public Hall createHall(Hall hall) {
        // If capacity is not provided or invalid, default to 100
        if (hall.getCapacity() == null || hall.getCapacity() <= 0) {
            hall.setCapacity(100);
        }
        
        Hall savedHall = repo.save(hall);
        
        int capacity = savedHall.getCapacity();
        int seatsPerRow = 10; // Fixed width of 10 seats per row

        for (int i = 0; i < capacity; i++) {
            int row = (i / seatsPerRow) + 1;
            int number = (i % seatsPerRow) + 1;

            Seat seat = new Seat();
            seat.setHall(savedHall);
            seat.setRowNumber(row);
            seat.setSeatNumber(number);
            seatRepo.save(seat);
        }
        
        return savedHall;
    }

    @Override
    public boolean deleteHallById(Integer id) {
        if (repo.findById(id).isEmpty()) {
            return false;
        }
        repo.deleteById(id);
        return true;
    }
}
