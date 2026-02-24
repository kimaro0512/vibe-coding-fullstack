package com.example.vibeapp.global;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
import java.util.List;

@Schema(name = "ErrorResponse", description = "공통 에러 응답 모델")
public record ErrorResponse(
        @Schema(description = "에러 발생 시각", example = "2026-02-24T15:30:00")
        LocalDateTime timestamp,
        @Schema(description = "HTTP 상태 코드", example = "404")
        int status,
        @Schema(description = "애플리케이션 에러 코드", example = "RESOURCE_NOT_FOUND")
        String code,
        @Schema(description = "에러 메시지", example = "Invalid post id: 99")
        String message,
        @Schema(description = "요청 경로", example = "/api/posts/99")
        String path,
        @Schema(description = "필드 검증 에러 목록")
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

    @Schema(name = "FieldErrorDetail", description = "필드 단위 검증 에러")
    public record FieldErrorDetail(
            @Schema(description = "검증 실패 필드명", example = "title")
            String field,
            @Schema(description = "검증 실패 메시지", example = "제목은 필수입니다.")
            String message
    ) {}
}
