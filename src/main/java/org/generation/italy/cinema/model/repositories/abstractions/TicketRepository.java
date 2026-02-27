package org.generation.italy.cinema.model.repositories.abstractions;

import org.generation.italy.cinema.model.entities.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketRepository extends JpaRepository<Ticket, Integer> {
}
