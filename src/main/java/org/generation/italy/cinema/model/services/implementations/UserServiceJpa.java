package org.generation.italy.cinema.model.services.implementations;

import org.generation.italy.cinema.model.entities.User;
import org.generation.italy.cinema.model.entities.UserRole;
import org.generation.italy.cinema.model.repositories.abstractions.UserRepository;
import org.generation.italy.cinema.model.services.abstractions.iUserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class UserServiceJpa implements iUserService {

    private final UserRepository userRepository;

    public UserServiceJpa(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // READ

    @Override
    public List<User> findAllUsers() {
        // SELECT * FROM users
        return userRepository.findAll();
    }

    @Override
    public Optional<User> findUserById(Integer id) {
        return userRepository.findById(id);
    }

    @Override
    public Optional<User> findUserByEmail(String email) {
        return userRepository.findByEmailIgnoreCase(email);
    }

    @Override
    public boolean emailAlreadyUsed(String email) {
        return userRepository.existsByEmailIgnoreCase(email);
    }

    // CREATE

    @Override
    public User createUser(User user) {
        // Regola: email unica
        if (userRepository.existsByEmailIgnoreCase(user.getEmail())) {
            throw new IllegalArgumentException("Email already used: " + user.getEmail());
        }

        // save() = INSERT
        return userRepository.save(user);
    }

    // UPDATE

    @Override
    public User updateUser(Integer id, User updated) {
        // 1) carico l'esistente
        User existing = userRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("User not found id=" + id));

        // 2) aggiorno campi (qui decidi cosa è modificabile)
        existing.setFirstName(updated.getFirstName());
        existing.setLastName(updated.getLastName());

        // se cambi email, controlla unicità
        if (updated.getEmail() != null && !updated.getEmail().equalsIgnoreCase(existing.getEmail())) {
            if (userRepository.existsByEmailIgnoreCase(updated.getEmail())) {
                throw new IllegalArgumentException("Email already used: " + updated.getEmail());
            }
            existing.setEmail(updated.getEmail());
        }

        // password: in genere si aggiorna con endpoint dedicato
        if (updated.getPassword() != null && !updated.getPassword().isBlank()) {
            existing.setPassword(updated.getPassword());
        }

        // role: di solito solo admin
        if (updated.getRole() != null) {
            existing.setRole(updated.getRole());
        }

        // save() = UPDATE
        return userRepository.save(existing);
    }

    @Override
    public User updateUserRole(Integer id, UserRole role) {
        User existing = userRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("User not found id=" + id));

        existing.setRole(role);
        return userRepository.save(existing);
    }

    // DELETE

    @Override
    public boolean deleteUserById(Integer id) {
        if (!userRepository.existsById(id)) return false;
        userRepository.deleteById(id);
        return true;
    }

    // ADMIN SEARCH / FILTER

    @Override
    public Page<User> findUsersByRole(UserRole role, Pageable pageable) {
        return userRepository.findByRole(role, pageable);
    }

    @Override
    public Page<User> searchUsersByName(String query, Pageable pageable) {
        // usa lo stesso "query" sia su firstName che lastName
        return userRepository
                .findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(query, query, pageable);
    }
}