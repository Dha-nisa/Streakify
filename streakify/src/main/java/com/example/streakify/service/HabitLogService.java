package com.example.streakify.service;

import com.example.streakify.dto.HabitLogRequestDto;
import com.example.streakify.dto.HabitLogResponseDto;
import com.example.streakify.dto.StreakResponseDto;
import com.example.streakify.dto.ViewLogsDto;
import com.example.streakify.exceptionHandling.BadRequestException;
import com.example.streakify.exceptionHandling.ResourceNotFoundException;
import com.example.streakify.model.Habit;
import com.example.streakify.model.HabitLog;
import com.example.streakify.repository.HabitLogsRepository;
import com.example.streakify.repository.HabitRepository;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

@Service
public class HabitLogService {

    private final HabitLogsRepository habitLogRepository;
    private final HabitRepository habitRepository;

    public HabitLogService(HabitLogsRepository habitLogRepository,
                           HabitRepository habitRepository) {
        this.habitLogRepository = habitLogRepository;
        this.habitRepository = habitRepository;
    }


    // CREATE LOG (returns weeklyStatus)

    public HabitLogResponseDto createLog(Long habitId,
                                         HabitLogRequestDto dto) {

        Habit habit = habitRepository.findById(habitId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Habit not found with id " + habitId));

        if (dto.getLogDate().isAfter(LocalDate.now())) {
            throw new BadRequestException("Cannot log future date");
        }

        if (habitLogRepository.existsByHabitIdAndLogDate(
                habitId, dto.getLogDate())) {
            throw new BadRequestException(
                    "Log already exists for this date");
        }

        HabitLog log = new HabitLog();
        log.setHabit(habit);
        log.setLogDate(dto.getLogDate());
        log.setCompleted(dto.getCompleted());

        HabitLog saved = habitLogRepository.save(log);

        String weeklyStatus =
                calculateWeeklyStatus(habit, saved.getLogDate());

        return new HabitLogResponseDto(
                saved.getId(),
                saved.getLogDate(),
                saved.getCompleted(),
                weeklyStatus
        );
    }


    // UPDATE LOG (returns weeklyStatus)

    public HabitLogResponseDto updateLog(Long habitId,
                                         LocalDate date,
                                         HabitLogRequestDto dto) {

        HabitLog log = habitLogRepository
                .findByHabitIdAndLogDate(habitId, date)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Log not found for date " + date));

        if (date.isAfter(LocalDate.now())) {
            throw new BadRequestException("Cannot update future date");
        }

        log.setCompleted(dto.getCompleted());

        HabitLog updated = habitLogRepository.save(log);

        String weeklyStatus =
                calculateWeeklyStatus(updated.getHabit(),
                        updated.getLogDate());

        return new HabitLogResponseDto(
                updated.getId(),
                updated.getLogDate(),
                updated.getCompleted(),
                weeklyStatus
        );
    }


    // GET LOGS (NO weeklyStatus)

    public List<ViewLogsDto> getLogs(Long habitId) {

        habitRepository.findById(habitId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Habit not found with id " + habitId));

        return habitLogRepository
                .findByHabitIdOrderByLogDateAsc(habitId)
                .stream()
                .map(log -> new ViewLogsDto(
                        log.getId(),
                        log.getLogDate(),
                        log.getCompleted()
                ))
                .toList();
    }


    // STREAK CALCULATION

    public StreakResponseDto getStreak(Long habitId) {

        habitRepository.findById(habitId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Habit not found with id " + habitId));

        List<HabitLog> logs =
                habitLogRepository.findByHabitIdOrderByLogDateAsc(habitId);

        if (logs.isEmpty()) {
            return new StreakResponseDto(0, 0);
        }

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

        int currentStreak = 0;
        LocalDate today = LocalDate.now();
        LocalDate expectedDate = today;

        for (int i = logs.size() - 1; i >= 0; i--) {

            HabitLog log = logs.get(i);

            if (!Boolean.TRUE.equals(log.getCompleted())) break;

            if (log.getLogDate().equals(expectedDate)) {
                currentStreak++;
                expectedDate = expectedDate.minusDays(1);
            } else {
                break;
            }
        }

        return new StreakResponseDto(currentStreak, longestStreak);
    }


    // WEEKLY TARGET CHECK

    private String calculateWeeklyStatus(Habit habit,
                                         LocalDate referenceDate) {

        LocalDate startOfWeek =
                referenceDate.with(DayOfWeek.MONDAY);
        LocalDate endOfWeek =
                referenceDate.with(DayOfWeek.SUNDAY);

        int completedCount = habitLogRepository
                .countByHabitIdAndCompletedTrueAndLogDateBetween(
                        habit.getId(),
                        startOfWeek,
                        endOfWeek
                );

        int target = habit.getTargetDaysPerWeek();

        if (completedCount < target) {
            return "In Progress";
        } else if (completedCount == target) {
            return "Target Achieved!";
        } else {
            return "Target Exceeded! Keep Going!";
        }
    }
}