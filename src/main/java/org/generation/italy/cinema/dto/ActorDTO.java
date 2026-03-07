package org.generation.italy.cinema.dto;

import org.generation.italy.cinema.model.entities.Actor;

import java.time.LocalDate;

public class ActorDTO {

    private Integer id;
    private String firstName;
    private String lastName;
    private String birthDate;
    private String nationality;

    public ActorDTO() {}

    public static ActorDTO fromEntity(Actor actor) {
        if (actor == null) return null;

        ActorDTO dto = new ActorDTO();
        dto.setId(actor.getId());
        dto.setFirstName(actor.getFirstName());
        dto.setLastName(actor.getLastName());
        dto.setBirthDate(actor.getBirthDate());
        dto.setNationality(actor.getNationality());
        return dto;
    }

    public static Actor toEntity(ActorDTO dto) {
        if (dto == null) return null;

        Actor actor = new Actor();
        actor.setId(dto.getId());
        actor.setFirstName(dto.getFirstName());
        actor.setLastName(dto.getLastName());
        actor.setBirthDate(dto.getBirthDate());
        actor.setNationality(dto.getNationality());
        return actor;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }
}