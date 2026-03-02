package org.generation.italy.cinema.model.entities;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(
        name = "service", // puoi lasciarla così se nel DB si chiama ancora "service"
        schema = "public",
        uniqueConstraints = @UniqueConstraint(
                name = "service_name_key",
                columnNames = "name"
        )
)
public class CinemaService {
    // Rinominata da Service a ExtraProduct per evitare conflitto con
// org.springframework.stereotype.Service (@Service) negli import.
// Java non può gestire due classi con lo stesso nome nello stesso file.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_service")
    private Integer id;

    @Column(name = "name", length = 100, nullable = false)
    private String name;

    @Column(name = "description", columnDefinition = "text")
    private String description;

    @Column(name = "price", precision = 8, scale = 2, nullable = false)
    private BigDecimal price;

    @OneToMany(mappedBy = "service")
    private List<BookedService> bookedServices = new ArrayList<>();

    public CinemaService() {
    }

    // GETTER & SETTER

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public List<BookedService> getBookedServices() {
        return bookedServices;
    }

    public void setBookedServices(List<BookedService> bookedServices) {
        this.bookedServices = bookedServices;
    }
}