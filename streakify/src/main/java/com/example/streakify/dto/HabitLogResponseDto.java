package com.example.streakify.dto;

import java.time.LocalDate;

public class HabitLogResponseDto {

    private Long id;
    private LocalDate logDate;
    private Boolean completed;
    private String weeklyStatus;

    public HabitLogResponseDto(Long id,
                               LocalDate logDate,
                               Boolean completed,
                               String weeklyStatus) {
        this.id = id;
        this.logDate = logDate;
        this.completed = completed;
        this.weeklyStatus = weeklyStatus;
    }

    public Long getId() {
        return id;
    }

    public LocalDate getLogDate() {
        return logDate;
    }

    public Boolean getCompleted() {
        return completed;
    }

    public String getWeeklyStatus() {
        return weeklyStatus;
    }
}