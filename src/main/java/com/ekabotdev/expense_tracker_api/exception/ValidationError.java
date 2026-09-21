package com.ekabotdev.expense_tracker_api.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
public class ValidationError {
    private int status;
    private String message;
    private LocalDateTime timestamp;
    private Map<String, String> errors;
}
