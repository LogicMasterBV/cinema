package org.generation.italy.cinema.dto;

import org.generation.italy.cinema.model.entities.UserRole;

import java.time.LocalDateTime;

/*
 * DTO unico per User.
 *
 * Viene usato:
 * - come Request Body per POST / PUT
 * - come Response Body per GET
 *
 * Nota:
 * - id e createdAt vengono ignorati in creazione (li imposta il DB)
 * - password può essere null in risposta se nel controller la escludi
 */
public class UserDTO {

    public Integer id;

    public String firstName;
    public String lastName;
    public String email;

    public String password; // attenzione: non restituirla se non necessario

    public UserRole role;

    public LocalDateTime createdAt;
}