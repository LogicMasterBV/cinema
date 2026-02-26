package org.generation.italy.cinema.model.entities;


import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(
        name = "genre",
        schema = "public",
        uniqueConstraints = @UniqueConstraint(name = "genre_name_key", columnNames = "name")
)
public class Genre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_genre")
    private Integer id;

    @Column(name = "name", length = 100, nullable = false)
    private String name;

    @ManyToMany(mappedBy = "genres")
    private Set<Film> films = new HashSet<>();

    public Genre() {}

    // getters/setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Set<Film> getFilms() { return films; }
    public void setFilms(Set<Film> films) { this.films = films; }
}
