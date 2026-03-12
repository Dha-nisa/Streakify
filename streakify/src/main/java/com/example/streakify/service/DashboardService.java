package com.example.streakify.service;

import com.example.streakify.dto.DashboardResponseDto;
import com.example.streakify.dto.StreakInfoDto;
import com.example.streakify.dto.StreakInfoDto;
import com.example.streakify.exceptionHandling.ResourceNotFoundException;
import com.example.streakify.model.Habit;
import com.example.streakify.model.HabitLog;
import com.example.streakify.repository.HabitLogsRepository;
import com.example.streakify.repository.HabitRepository;
import com.example.streakify.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class DashboardService {

    private final UserRepository userRepository;
    private final HabitRepository habitRepository;
    private final HabitLogsRepository habitLogRepository;
    private final StreakService streakService;

    public DashboardService(
            UserRepository userRepository,
            HabitRepository habitRepository,
            HabitLogsRepository habitLogRepository,
            StreakService streakService) {

        this.userRepository = userRepository;
        this.habitRepository = habitRepository;
        this.habitLogRepository = habitLogRepository;
        this.streakService = streakService;
    }

    public DashboardResponseDto getDashboard(Long userId) {

        userRepository.findById(userId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        // TOTAL HABITS
        long totalHabits = habitRepository.countByUserId(userId);

        // COMPLETED TODAY
        long completedToday =
                habitLogRepository
                        .countByHabit_UserIdAndLogDateAndCompletedTrue(
                                userId,
                                LocalDate.now()
                        );

        // GET USER HABITS
        List<Habit> habits = habitRepository.findByUserId(userId);

        // ACTIVE HABITS (logged in last 7 days)
        long activeHabits = habits.stream()
                .filter(habit ->
                        habitLogRepository.existsByHabitIdAndLogDateAfter(
                                habit.getId(),
                                LocalDate.now().minusDays(7)
                        )
                )
                .count();

        // STREAK SUMMARY
        List<StreakInfoDto> streaks =
                habits.stream()
                        .map(habit -> {
                            var streak =
                                    streakService.getStreak(habit.getId());

                            return new StreakInfoDto(
                                    habit.getName(),
                                    streak.getCurrentStreak(),
                                    streak.getLongestStreak()
                            );
                        })
                        .toList();

        int consistencyScore = calculateConsistency(userId);

        return new DashboardResponseDto(
                totalHabits,
                activeHabits,
                completedToday,
                streaks,
                consistencyScore
        );
    }

    // CONSISTENCY SCORE
    private int calculateConsistency(Long userId) {

        List<Habit> habits = habitRepository.findByUserId(userId);

        if (habits.isEmpty()) return 0;

        LocalDate today = LocalDate.now();
        LocalDate startOfWeek = today.with(DayOfWeek.MONDAY);

        long daysPassed =
                ChronoUnit.DAYS.between(startOfWeek, today) + 1;

        long completedThisWeek = 0;

        for (Habit habit : habits) {

            List<HabitLog> logs =
                    habitLogRepository
                            .findByHabitIdOrderByLogDateAsc(habit.getId());

            for (HabitLog log : logs) {

                if (Boolean.TRUE.equals(log.getCompleted())
                        && !log.getLogDate().isBefore(startOfWeek)
                        && !log.getLogDate().isAfter(today)) {

                    completedThisWeek++;
                }
            }
        }

        int totalWeeklyTarget = habits.stream()
                .mapToInt(Habit::getTargetDaysPerWeek)
                .sum();

        double expectedTillToday =
                totalWeeklyTarget * (daysPassed / 7.0);

        if (expectedTillToday == 0) return 0;

        int score =
                (int) ((completedThisWeek * 100) / expectedTillToday);

        return Math.min(score, 100);
    }
}