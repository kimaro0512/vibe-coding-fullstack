package com.example.vibeapp.post.dto;

import com.example.vibeapp.post.Post;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
import java.util.List;

@Schema(name = "PostResponseDTO", description = "게시글 상세 응답")
public record PostResponseDTO(
    @Schema(description = "게시글 번호", example = "1")
    Long id,
    @Schema(description = "게시글 제목", example = "OpenAPI 적용 완료")
    String title,
    @Schema(description = "게시글 내용", example = "Swagger UI에서 Bearer 토큰 테스트가 가능하다.")
    String content,
    @Schema(description = "생성 시각", example = "2026-02-24T16:15:00")
    LocalDateTime createdAt,
    @Schema(description = "수정 시각", example = "2026-02-24T18:00:00")
    LocalDateTime updatedAt,
    @Schema(description = "조회수", example = "10")
    Integer views,
    @ArraySchema(schema = @Schema(description = "태그", example = "spring"))
    List<String> tags
) {
    public static PostResponseDTO from(Post entity) {
        return from(entity, List.of());
    }

    public static PostResponseDTO from(Post entity, List<String> tags) {
        return new PostResponseDTO(
            entity.getId(),
            entity.getTitle(),
            entity.getContent(),
            entity.getCreatedAt(),
            entity.getUpdatedAt(),
            entity.getViews(),
            tags
        );
    }
}
