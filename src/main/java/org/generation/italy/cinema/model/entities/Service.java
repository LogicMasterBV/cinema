package org.generation.italy.cinema.model.entities;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(
        name = "service",
        schema = "public",
        uniqueConstraints = @UniqueConstraint(name = "service_name_key", columnNames = "name")
)
public class Service {

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
    private List<BookingService> bookingServices = new ArrayList<>();

    public Service() {}

    // getters/setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }

    public List<BookingService> getBookingServices() { return bookingServices; }
    public void setBookingServices(List<BookingService> bookingServices) { this.bookingServices = bookingServices; }
}