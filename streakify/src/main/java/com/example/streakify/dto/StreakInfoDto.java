package com.example.streakify.dto;
import lombok.*;

@Getter
public class StreakInfoDto {

    private String habitName;
    private int currentStreak;
    private int longestStreak;

    public StreakInfoDto(String habitName,
                            int currentStreak,
                            int longestStreak) {
        this.habitName = habitName;
        this.currentStreak = currentStreak;
        this.longestStreak = longestStreak;
    }


}