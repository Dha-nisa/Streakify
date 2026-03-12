package com.example.streakify.repository;

import com.example.streakify.model.HabitLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface HabitLogsRepository extends JpaRepository<HabitLog, Long> {

    // Check duplicate log for same habit and date
    boolean existsByHabitIdAndLogDate(Long habitId, LocalDate logDate);

    // Find log for update
    Optional<HabitLog> findByHabitIdAndLogDate(Long habitId, LocalDate logDate);

    // Get logs in chronological order
    List<HabitLog> findByHabitIdOrderByLogDateAsc(Long habitId);

    long countByHabitUserIdAndLogDateAndCompletedTrue(
            Long userId,
            LocalDate date);

    long countByHabitUserIdAndCompletedTrueAndLogDateBetween(
            Long userId,
            LocalDate start,
            LocalDate end);


    //Active habits check
    boolean existsByHabitIdAndLogDateAfter(
            Long habitId,
            LocalDate date
    );

    int countByHabitIdAndCompletedTrueAndLogDateBetween(Long id, LocalDate startOfWeek, LocalDate endOfWeek);

    long countByHabit_UserIdAndLogDateAndCompletedTrue(Long userId, LocalDate now);
}