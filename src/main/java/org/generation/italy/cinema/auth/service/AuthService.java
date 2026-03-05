package org.generation.italy.cinema.auth.service;

import org.generation.italy.cinema.auth.dto.AuthResponse;
import org.generation.italy.cinema.auth.dto.LoginRequest;
import org.generation.italy.cinema.auth.dto.RegisterRequest;
import org.generation.italy.cinema.model.entities.User;
import org.generation.italy.cinema.model.entities.UserRole;
import org.generation.italy.cinema.model.repositories.abstractions.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JWTService jwtService;

    public AuthService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       AuthenticationManager authenticationManager,
                       JWTService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    public void register(RegisterRequest req) {
        if (userRepository.existsByEmailIgnoreCase((req.email))){
            throw new IllegalArgumentException("Email already in use");
        }

        User u = new User();
        u.setFirstName(req.firstName);
        u.setLastName(req.lastName);
        u.setEmail(req.email);
        u.setPassword(passwordEncoder.encode(req.password));
        u.setRole(UserRole.customer); // default, decidi tu

        userRepository.save(u);
    }

    public AuthResponse login(LoginRequest req) {
        // se password/email sbagliate -> eccezione, Spring blocca
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(req.email, req.password)
        );

        User user = userRepository.findByEmailIgnoreCase(req.email)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        String token = jwtService.generateToken(user);
        return new AuthResponse(token, user.getId(), user.getEmail(), user.getFirstName(), user.getLastName(), user.getRole());
    }
}