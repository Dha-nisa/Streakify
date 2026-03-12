package com.example.streakify.controller;

import com.example.streakify.dto.StreakResponseDto;
import com.example.streakify.service.HabitLogService;
import com.example.streakify.service.StreakService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/habits")
public class StreakController {

    @Autowired
    private StreakService streakService;

    @GetMapping("/{habitId}/streak")
    public ResponseEntity<StreakResponseDto> getStreak(
            @PathVariable Long habitId) {

        StreakResponseDto response =
                streakService.getStreak(habitId);

        return ResponseEntity.ok(response);
    }
}