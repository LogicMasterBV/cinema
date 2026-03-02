package org.generation.italy.cinema.model.repositories.abstractions;

import org.generation.italy.cinema.model.entities.User;
import org.generation.italy.cinema.model.entities.UserRole;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    // LOGIN / REGISTRAZIONE
    Optional<User> findByEmailIgnoreCase(String email);

    boolean existsByEmailIgnoreCase(String email);

    // FILTRI ADMIN
    Page<User> findByRole(UserRole role, Pageable pageable);

    // Ricerca per nome/cognome (utile per admin)
    Page<User> findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(
            String firstName,
            String lastName,
            Pageable pageable
    );
}