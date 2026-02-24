package com.example.vibeapp.post.dto;

import com.example.vibeapp.post.Post;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

@Schema(name = "PostListDto", description = "게시글 목록 응답 항목")
public record PostListDto(
    @Schema(description = "게시글 번호", example = "1")
    Long id,
    @Schema(description = "게시글 제목", example = "OpenAPI 자동화 적용")
    String title,
    @Schema(description = "생성 시각", example = "2026-02-24T16:15:00")
    LocalDateTime createdAt,
    @Schema(description = "조회수", example = "42")
    Integer views
) {
    public static PostListDto from(Post entity) {
        return new PostListDto(
            entity.getId(),
            entity.getTitle(),
            entity.getCreatedAt(),
            entity.getViews()
        );
    }
}
