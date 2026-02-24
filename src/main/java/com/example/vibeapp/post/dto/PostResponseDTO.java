package com.example.vibeapp.post.dto;

import com.example.vibeapp.post.Post;
import java.time.LocalDateTime;
import java.util.List;

public record PostResponseDTO(
    Long id,
    String title,
    String content,
    LocalDateTime createdAt,
    LocalDateTime updatedAt,
    Integer views,
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
