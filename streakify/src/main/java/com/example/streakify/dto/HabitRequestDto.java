package com.example.streakify.dto;

import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
public class HabitRequestDto {

    @NotBlank(message = "Habit name is required")
    private String name;

    @NotNull(message = "Target days per week is required")
    @Min(value = 1, message = "Minimum 1 day")
    @Max(value = 7, message = "Maximum 7 days")
    private Integer targetDaysPerWeek;

    @NotNull(message = "User ID is required")
    private Long userId;

    public HabitRequestDto() {}

    public HabitRequestDto(String name, Integer targetDaysPerWeek, Long userId) {
        this.name = name;
        this.targetDaysPerWeek = targetDaysPerWeek;
        this.userId = userId;
    }

}
