package com.example.streakify.dto;
import lombok.*;
import java.time.LocalDateTime;

@Getter
@Setter
public class HabitResponseDto{

    private Long id;
    private String name;
    private Integer targetDaysPerWeek;
    private LocalDateTime createdAt;

    public HabitResponseDto() {}

    public HabitResponseDto(Long id, String name,
                            Integer targetDaysPerWeek,
                            LocalDateTime createdAt) {
        this.id = id;
        this.name = name;
        this.targetDaysPerWeek = targetDaysPerWeek;
        this.createdAt = createdAt;
    }

}
