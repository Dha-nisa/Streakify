package com.example.streakify.controller;

import com.example.streakify.dto.HabitRequestDto;
import com.example.streakify.dto.HabitResponseDto;
import com.example.streakify.service.HabitService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping
public class HabitController {

    private final HabitService habitService;

    public HabitController(HabitService habitService) {
        this.habitService = habitService;
    }

    // POST /habits
    @PostMapping("/habits")
    public ResponseEntity<HabitResponseDto> createHabit(
            @Valid @RequestBody HabitRequestDto dto) {

        HabitResponseDto response = habitService.createHabit(dto);
        return ResponseEntity.status(201).body(response);
    }

    // GET /users/{userId}/habits
    @GetMapping("/users/{userId}/habits")
    public ResponseEntity<List<HabitResponseDto>> getHabitsByUser(
            @PathVariable Long userId) {

        return ResponseEntity.ok(
                habitService.getHabitsByUser(userId));
    }

    // DELETE /habits/{id}
    @DeleteMapping("/habits/{id}")
    public ResponseEntity<String> deleteHabit(
            @PathVariable Long id) {

        habitService.deleteHabit(id);
        return ResponseEntity.ok("Habit deleted successfully");
    }
}