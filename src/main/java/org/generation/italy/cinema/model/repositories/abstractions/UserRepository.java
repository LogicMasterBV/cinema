package org.generation.italy.cinema.model.repositories.abstractions;

import org.generation.italy.cinema.model.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
}
