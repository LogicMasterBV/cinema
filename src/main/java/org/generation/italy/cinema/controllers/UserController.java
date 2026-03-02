package org.generation.italy.cinema.controllers;

import org.generation.italy.cinema.dto.UserDTO;
import org.generation.italy.cinema.model.entities.User;
import org.generation.italy.cinema.model.entities.UserRole;
import org.generation.italy.cinema.model.services.abstractions.iUserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final iUserService userService;

    public UserController(iUserService userService) {
        this.userService = userService;
    }

    // -----------------------------
    // Mapping: Entity -> DTO
    // -----------------------------
    private UserDTO toDto(User u) {
        UserDTO dto = new UserDTO();
        dto.id = u.getId();
        dto.firstName = u.getFirstName();
        dto.lastName = u.getLastName();
        dto.email = u.getEmail();
        dto.role = u.getRole();
        dto.createdAt = u.getCreatedAt();
        dto.password = null; // sempre null in response
        return dto;
    }

    // -----------------------------
    // Mapping: DTO -> Entity
    // -----------------------------
    private User fromDto(UserDTO dto) {
        User u = new User();
        u.setFirstName(dto.firstName);
        u.setLastName(dto.lastName);
        u.setEmail(dto.email);
        u.setPassword(dto.password);
        u.setRole(dto.role);
        return u;
    }

    // GET /api/users (paginato)
    @GetMapping
    public ResponseEntity<List<UserDTO>> getAll() {
        List<UserDTO> users = userService.findAllUsers()
                .stream()
                .map(u -> toDto(u)).toList();
        return ResponseEntity.ok(users);
    }

    // GET /api/users/{id}
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getById(@PathVariable Integer id) {
        return userService.findUserById(id)
                .map(u -> ResponseEntity.ok(toDto(u)))
                .orElse(ResponseEntity.notFound().build());
    }

    // GET /api/users/by-email?email=...
    @GetMapping("/by-email")
    public ResponseEntity<UserDTO> getByEmail(@RequestParam String email) {
        return userService.findUserByEmail(email)
                .map(u -> ResponseEntity.ok(toDto(u)))
                .orElse(ResponseEntity.notFound().build());
    }

    // GET /api/users/by-role?role=admin
    @GetMapping("/by-role")
    public ResponseEntity<Page<UserDTO>> getByRole(@RequestParam UserRole role, Pageable pageable) {
        Page<UserDTO> page = userService.findUsersByRole(role, pageable)
                .map(u -> toDto(u));
        return ResponseEntity.ok(page);
    }

    // GET /api/users/search?q=mario
    @GetMapping("/search")
    public ResponseEntity<Page<UserDTO>> search(@RequestParam String q, Pageable pageable) {
        Page<UserDTO> page = userService.searchUsersByName(q, pageable)
                .map(u -> toDto(u));
        return ResponseEntity.ok(page);
    }

    // POST /api/users
    @PostMapping
    public ResponseEntity<UserDTO> create(@RequestBody UserDTO dto) {

        // Se vuoi imporre sempre customer lato server, decommenta:
        // dto.role = UserRole.customer;

        User created = userService.createUser(fromDto(dto));

        UserDTO response = toDto(created);

        return ResponseEntity
                .created(URI.create("/api/users/" + created.getId()))
                .body(response);
    }

    // PUT /api/users/{id}
    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> update(@PathVariable Integer id,
                                          @RequestBody UserDTO dto) {

        User updated = userService.updateUser(id, fromDto(dto));
        return ResponseEntity.ok(toDto(updated));
    }

    // DELETE /api/users/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        boolean deleted = userService.deleteUserById(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}