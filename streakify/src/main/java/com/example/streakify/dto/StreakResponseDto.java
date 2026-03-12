package com.example.streakify.dto;

public class StreakResponseDto {

    private int currentStreak;
    private int longestStreak;

    public StreakResponseDto(int currentStreak, int longestStreak) {
        this.currentStreak = currentStreak;
        this.longestStreak = longestStreak;
    }

    public int getCurrentStreak() {
        return currentStreak;
    }

    public int getLongestStreak() {
        return longestStreak;
    }
}