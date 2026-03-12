package com.example.streakify.dto;


import java.time.LocalDate;

    public class ViewLogsDto {

        private Long id;
        private LocalDate logDate;
        private boolean completed;

        public ViewLogsDto(Long id,
                                   LocalDate logDate,
                                   boolean completed) {
            this.id = id;
            this.logDate = logDate;
            this.completed = completed;
        }

        public Long getId() {
            return id;
        }

        public LocalDate getLogDate() {
            return logDate;
        }

        public boolean isCompleted() {
            return completed;
        }
    }

