package com.example.vibeapp.global;

import java.time.LocalDateTime;
import java.util.List;

public record ErrorResponse(
        LocalDateTime timestamp,
        int status,
        String code,
        String message,
        String path,
        List<FieldErrorDetail> fieldErrors
) {
    public static ErrorResponse of(int status, String code, String message, String path) {
        return new ErrorResponse(LocalDateTime.now(), status, code, message, path, List.of());
    }

    public static ErrorResponse of(
            int status,
            String code,
            String message,
            String path,
            List<FieldErrorDetail> fieldErrors
    ) {
        return new ErrorResponse(LocalDateTime.now(), status, code, message, path, fieldErrors);
    }

    public record FieldErrorDetail(String field, String message) {}
}
