package org.generation.italy.cinema.model.services.implementations;

import org.generation.italy.cinema.model.entities.Hall;
import org.generation.italy.cinema.model.entities.Seat;
import org.generation.italy.cinema.model.repositories.abstractions.HallRepository;
import org.generation.italy.cinema.model.repositories.abstractions.SeatRepository;
import org.generation.italy.cinema.model.services.abstractions.iSeatService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SeatService implements iSeatService {

    private final SeatRepository seatRepository;
    private final HallRepository hallRepository;

    public SeatService(SeatRepository seatRepository, HallRepository hallRepository) {
        this.seatRepository = seatRepository;
        this.hallRepository = hallRepository;
    }

    @Override
    public List<Seat> getAll() {
        return seatRepository.findAll();
    }

    @Override
    public Seat getById(int id) {
        return seatRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Seat non trovato: " + id));
    }

    @Override
    public List<Seat> getByHallId(int hallId) {
        return seatRepository.findSeatsByHall(hallId);
    }

    @Override
    public Seat create(Seat seat) {
        // controlli minimi
        if (seat.getHall() == null || seat.getHall().getId() == null) {
            throw new IllegalArgumentException("Hall obbligatoria (hall.id)");
        }
        if (seat.getRowNumber() == null || seat.getSeatNumber() == null) {
            throw new IllegalArgumentException("rowNumber e seatNumber sono obbligatori");
        }

        Integer hallId = seat.getHall().getId();

        // controllo duplicato
        if (seatRepository.seatExists(hallId, seat.getRowNumber(), seat.getSeatNumber())) {
            throw new IllegalArgumentException("Posto già esistente per questa sala (row, seatNumber)");
        }

        // attacco una Hall “vera” presa dal DB (evita oggetti Hall finti arrivati dal client)
        Hall hall = hallRepository.findById(hallId)
                .orElseThrow(() -> new IllegalArgumentException("Hall non trovata: " + hallId));

        seat.setHall(hall);

        return seatRepository.save(seat);
    }

    @Override
    public Seat update(int id, Seat seatData) {
        Seat seat = seatRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Seat non trovato: " + id));

        // se vuoi permettere cambio sala:
        if (seatData.getHall() != null && seatData.getHall().getId() != null) {
            Hall hall = hallRepository.findById(seatData.getHall().getId())
                    .orElseThrow(() -> new IllegalArgumentException("Hall non trovata: " + seatData.getHall().getId()));
            seat.setHall(hall);
        }

        // aggiorno campi se presenti
        if (seatData.getRowNumber() != null) seat.setRowNumber(seatData.getRowNumber());
        if (seatData.getSeatNumber() != null) seat.setSeatNumber(seatData.getSeatNumber());

        // controllo duplicato (attenzione: qui dovresti escludere “se stesso”)
        Integer hallId = seat.getHall().getId();
        Integer row = seat.getRowNumber();
        Integer num = seat.getSeatNumber();
        if (seatRepository.seatExists(hallId, row, num)) {
            // qui idealmente fai un controllo più preciso con id
            // oppure gestisci con vincolo UNIQUE a DB e catturi l'eccezione
        }

        return seatRepository.save(seat);
    }

    @Override
    public boolean delete(int id) {
        if (!seatRepository.existsById(id)) return false;
        seatRepository.deleteById(id);
        return true;
    }
}