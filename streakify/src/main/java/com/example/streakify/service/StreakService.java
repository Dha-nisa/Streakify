package com.example.streakify.service;

import com.example.streakify.dto.StreakResponseDto;
import com.example.streakify.exceptionHandling.ResourceNotFoundException;
import com.example.streakify.model.HabitLog;
import com.example.streakify.repository.HabitLogsRepository;
import com.example.streakify.repository.HabitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class StreakService {

    @Autowired
    private HabitLogsRepository habitLogRepository;

    @Autowired
    private HabitRepository habitRepository;

    public StreakResponseDto getStreak(Long habitId) {

        // Validate habit exists
        habitRepository.findById(habitId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Habit not found with id " + habitId));

        List<HabitLog> logs =
                habitLogRepository.findByHabitIdOrderByLogDateAsc(habitId);

        if (logs.isEmpty()) {
            return new StreakResponseDto(0, 0);
        }


        // LONGEST STREAK

        int longestStreak = 0;
        int tempStreak = 0;
        LocalDate previousDate = null;

        for (HabitLog log : logs) {

            if (Boolean.TRUE.equals(log.getCompleted())) {

                if (previousDate == null ||
                        previousDate.plusDays(1).equals(log.getLogDate())) {
                    tempStreak++;
                } else {
                    tempStreak = 1;
                }

                longestStreak = Math.max(longestStreak, tempStreak);
                previousDate = log.getLogDate();

            } else {
                tempStreak = 0;
                previousDate = null;
            }
        }


        // CURRENT STREAK

        int currentStreak = 0;

        LocalDate today = LocalDate.now();
        LocalDate expectedDate = today;

        LocalDate lastLogDate = logs.get(logs.size() - 1).getLogDate();

        // If yesterday was last completed day and today not logged yet
        if (lastLogDate.equals(today.minusDays(1))) {
            expectedDate = lastLogDate;
        }

        for (int i = logs.size() - 1; i >= 0; i--) {

            HabitLog log = logs.get(i);

            if (!Boolean.TRUE.equals(log.getCompleted())) {
                break;
            }

            if (log.getLogDate().equals(expectedDate)) {
                currentStreak++;
                expectedDate = expectedDate.minusDays(1);
            } else {
                break;
            }
        }

        return new StreakResponseDto(currentStreak, longestStreak);
    }
}