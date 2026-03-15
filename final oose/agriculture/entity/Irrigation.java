package com.agriculture.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "irrigation")
public class Irrigation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String fieldName;
    
    @Column(nullable = false)
    private String cropName;
    
    @Enumerated(EnumType.STRING)
    private Method method;
    
    @Column(nullable = false)
    private LocalDate scheduleDate;
    
    @Column(nullable = false)
    private LocalTime scheduleTime;
    
    @Column(nullable = false)
    private Integer waterAmount;
    
    private Boolean completed = false;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    
    public enum Method {
        DRIP, SPRINKLER, FLOOD, FURROW
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getFieldName() { return fieldName; }
    public void setFieldName(String fieldName) { this.fieldName = fieldName; }
    public String getCropName() { return cropName; }
    public void setCropName(String cropName) { this.cropName = cropName; }
    public Method getMethod() { return method; }
    public void setMethod(Method method) { this.method = method; }
    public LocalDate getScheduleDate() { return scheduleDate; }
    public void setScheduleDate(LocalDate scheduleDate) { this.scheduleDate = scheduleDate; }
    public LocalTime getScheduleTime() { return scheduleTime; }
    public void setScheduleTime(LocalTime scheduleTime) { this.scheduleTime = scheduleTime; }
    public Integer getWaterAmount() { return waterAmount; }
    public void setWaterAmount(Integer waterAmount) { this.waterAmount = waterAmount; }
    public Boolean getCompleted() { return completed; }
    public void setCompleted(Boolean completed) { this.completed = completed; }
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
}