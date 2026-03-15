package com.agriculture.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "crops")
public class Crop {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String name;
    
    private String variety;
    
    @Column(nullable = false)
    private Double area;
    
    @Column(nullable = false)
    private LocalDate plantedDate;
    
    @Column(nullable = false)
    private LocalDate harvestDate;
    
    @Enumerated(EnumType.STRING)
    private Status status = Status.PLANTED;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    
    public enum Status {
        PLANTED, GROWING, HARVESTING, COMPLETED
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getVariety() { return variety; }
    public void setVariety(String variety) { this.variety = variety; }
    public Double getArea() { return area; }
    public void setArea(Double area) { this.area = area; }
    public LocalDate getPlantedDate() { return plantedDate; }
    public void setPlantedDate(LocalDate plantedDate) { this.plantedDate = plantedDate; }
    public LocalDate getHarvestDate() { return harvestDate; }
    public void setHarvestDate(LocalDate harvestDate) { this.harvestDate = harvestDate; }
    public Status getStatus() { return status; }
    public void setStatus(Status status) { this.status = status; }
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
}