package com.example.streakify.dto;

import lombok.Getter;
import java.util.List;

@Getter
public class DashboardResponseDto {

    private final long totalHabits;
    private final long activeHabits;
    private final long completedToday;
    private final List<StreakInfoDto> currentStreaks;
    private final int consistencyScore;

    public DashboardResponseDto(long totalHabits,
                                long activeHabits,
                                long completedToday,
                                List<StreakInfoDto> currentStreaks,
                                int consistencyScore) {
        this.totalHabits = totalHabits;
        this.activeHabits = activeHabits;
        this.completedToday = completedToday;
        this.currentStreaks = currentStreaks;
        this.consistencyScore = consistencyScore;
    }
}