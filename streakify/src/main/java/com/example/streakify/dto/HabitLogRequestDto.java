package com.example.streakify.dto;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import java.time.LocalDate;

@Getter
@Setter
public class HabitLogRequestDto {

    @NotNull(message = "Log date is required")
    private LocalDate logDate;

    @NotNull(message = "Completed status is required")
    private Boolean completed;
}