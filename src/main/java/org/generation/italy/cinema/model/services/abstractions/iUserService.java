package org.generation.italy.cinema.model.services.abstractions;

import org.generation.italy.cinema.model.entities.User;
import org.generation.italy.cinema.model.entities.UserRole;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface iUserService {

    // CRUD base
    List<User> findAllUsers();
    Optional<User> findUserById(Integer id);
    Optional<User> findUserByEmail(String email);

    User createUser(User user);
    User updateUser(Integer id, User updated);

    boolean deleteUserById(Integer id);

    // utility
    boolean emailAlreadyUsed(String email);

    // admin features
    Page<User> findUsersByRole(UserRole role, Pageable pageable);
    Page<User> searchUsersByName(String query, Pageable pageable);

    // opzionale: cambia ruolo
    User updateUserRole(Integer id, UserRole role);
}