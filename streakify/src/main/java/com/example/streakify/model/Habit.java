package com.example.streakify.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;


@Entity
@Table(name = "habits")
public class Habit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(name = "target_days_per_week")
    private Integer targetDaysPerWeek;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private Users user;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @PrePersist
    public void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getTargetDaysPerWeek() {
        return targetDaysPerWeek;
    }

    public Users getUser() {
        return user;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTargetDaysPerWeek(Integer targetDaysPerWeek) {
        this.targetDaysPerWeek = targetDaysPerWeek;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
