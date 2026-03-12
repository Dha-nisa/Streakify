package com.example.streakify.controller;

import com.example.streakify.dto.HabitLogRequestDto;
import com.example.streakify.dto.HabitLogResponseDto;
import com.example.streakify.dto.ViewLogsDto;
import com.example.streakify.service.HabitLogService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/habits")
public class HabitLogsController{

    private final HabitLogService habitLogService;

    public HabitLogsController(HabitLogService habitLogService) {
        this.habitLogService = habitLogService;                                                                                                                                 
    }

    // POST /habits/{habitId}/logs
    @PostMapping("/{habitId}/logs")
    public ResponseEntity<HabitLogResponseDto> createLog(
            @PathVariable Long habitId,
            @Valid @RequestBody HabitLogRequestDto dto) {

        HabitLogResponseDto response =
                habitLogService.createLog(habitId, dto);

        return ResponseEntity.status(201).body(response);
    }

    // PUT /habits/{habitId}/logs/{date}
    @PutMapping("/{habitId}/logs/{date}")
    public ResponseEntity<HabitLogResponseDto> updateLog(
            @PathVariable Long habitId,
            @PathVariable LocalDate date,
            @Valid @RequestBody HabitLogRequestDto dto) {

        HabitLogResponseDto response =
                habitLogService.updateLog(habitId, date, dto);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{habitId}/logs")
    public ResponseEntity<List<ViewLogsDto>> getLogs(
            @PathVariable Long habitId) {

        return ResponseEntity.ok(
                habitLogService.getLogs(habitId));
    }
}
