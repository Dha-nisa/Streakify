package com.example.streakify.service;

import com.example.streakify.dto.HabitRequestDto;
import com.example.streakify.dto.HabitResponseDto;
import com.example.streakify.exceptionHandling.ResourceNotFoundException;
import com.example.streakify.model.Habit;
import com.example.streakify.model.Users;
import com.example.streakify.repository.HabitRepository;
import com.example.streakify.repository.UserRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class HabitService {

    private final HabitRepository habitRepository;
    private final UserRepository userRepository;

    public HabitService(HabitRepository habitRepository,
                        UserRepository userRepository) {
        this.habitRepository = habitRepository;
        this.userRepository = userRepository;
    }

    // CREATE HABIT
    public HabitResponseDto createHabit(HabitRequestDto dto) {

        Users user = userRepository.findById(dto.getUserId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User not found with id " + dto.getUserId()));

        Habit habit = new Habit();
        habit.setName(dto.getName().trim());
        habit.setTargetDaysPerWeek(dto.getTargetDaysPerWeek());
        habit.setUser(user);

        Habit savedHabit = habitRepository.save(habit);

        return mapToDTO(savedHabit);
    }

    // GET ALL HABITS BY USER
    public List<HabitResponseDto> getHabitsByUser(Long userId) {

        if (!userRepository.existsById(userId)) {
            throw new ResourceNotFoundException(
                    "User not found with id " + userId);
        }

        return habitRepository.findByUserId(userId)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    // DELETE HABIT
    public void deleteHabit(Long habitId) {

        Habit habit = habitRepository.findById(habitId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Habit not found with id " + habitId));

        habitRepository.delete(habit);
    }

    private HabitResponseDto mapToDTO(Habit habit) {
        return new HabitResponseDto(
                habit.getId(),
                habit.getName(),
                habit.getTargetDaysPerWeek(),
                habit.getCreatedAt()
        );
    }
}