package org.generation.italy.cinema.auth.dto;

import org.generation.italy.cinema.model.entities.UserRole;

public class AuthResponse {
    public String token;
    public Integer id;
    public String email;
    public String firstName;
    public String lastName;
    public UserRole role;

    public AuthResponse(String token, Integer id, String email, String firstName, String lastName, UserRole role) {
        this.token = token;
        this.id = id;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
    }
}
