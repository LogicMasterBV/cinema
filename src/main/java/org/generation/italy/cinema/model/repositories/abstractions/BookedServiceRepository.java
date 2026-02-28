package org.generation.italy.cinema.model.repositories.abstractions;

import org.generation.italy.cinema.model.entities.BookedService;
import org.generation.italy.cinema.model.entities.BookedServiceId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookedServiceRepository extends JpaRepository<BookedService, BookedServiceId> {
    List<BookedService> findByBooking_Id(Integer bookingId);  // Restituisce tutti i servizi (cibi/bevande) associati ad una specifica prenotazione "Dammi tutto quello che l’utente ha ordinato nella prenotazione 5."
    // Qui mettiamo id nel naming method perchè parliamo con il DB -> Mentre in iBookedService non lo usiamo perchè è logica di business, il service non deve sapere che uso id nel repository
    List<BookedService> findByService_Id(Integer serviceId); // Restituisce tutte le prenotazioni in cui è stato ordinato uno specifico servizio "Quante volte è stato ordinato il popcorn grande?"
}
