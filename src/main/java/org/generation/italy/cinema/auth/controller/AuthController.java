package org.generation.italy.cinema.auth.controller;
import org.generation.italy.cinema.auth.dto.AuthResponse;
import org.generation.italy.cinema.auth.dto.LoginRequest;
import org.generation.italy.cinema.auth.dto.RegisterRequest;
import org.generation.italy.cinema.auth.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final org.generation.italy.cinema.auth.service.AuthService authService;

    public AuthController(org.generation.italy.cinema.auth.service.AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<Void> register(@Validated @RequestBody RegisterRequest req) {
        authService.register(req);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Validated @RequestBody LoginRequest req) {
        return ResponseEntity.ok(authService.login(req));
    }
}