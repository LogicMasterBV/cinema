package org.generation.italy.cinema.dto;

import org.generation.italy.cinema.model.entities.Director;

public class DirectorDTO {
    private Integer id;
    private String firstName;
    private String lastName;

    public DirectorDTO(){}

    public DirectorDTO(Integer id, String firstName, String lastName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }
    // entity -> dto
    public static DirectorDTO fromDirector(Director director){
        return new DirectorDTO(director.getId(), director.getFirstName(), director.getLastName());
    }
    // dto -> entity
    public Director toEntity(){
        if(this.id == null || this.id == 0){
            return new Director(null, firstName, lastName); // lascia null per auto-increment
        }
        return new Director(id, firstName, lastName);
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
}
