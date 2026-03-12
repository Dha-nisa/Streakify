package com.example.streakify.controller;

import com.example.streakify.dto.DashboardResponseDto;
import com.example.streakify.service.DashboardService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class DashboardController {

    private final DashboardService dashboardService;

    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping("/{userId}/dashboard")
    public ResponseEntity<DashboardResponseDto> getDashboard(
            @PathVariable Long userId) {

        return ResponseEntity.ok(
                dashboardService.getDashboard(userId)
        );
    }
}
