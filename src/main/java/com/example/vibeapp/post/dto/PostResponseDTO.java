package com.example.vibeapp.post.dto;

import com.example.vibeapp.post.Post;
import java.time.LocalDateTime;

public class PostResponseDTO {
    private Long id;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Integer views;

    public PostResponseDTO() {}

    public PostResponseDTO(Long id, String title, String content, LocalDateTime createdAt, LocalDateTime updatedAt, Integer views) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.views = views;
    }

    public static PostResponseDTO from(Post entity) {
        return new PostResponseDTO(
            entity.getId(),
            entity.getTitle(),
            entity.getContent(),
            entity.getCreatedAt(),
            entity.getUpdatedAt(),
            entity.getViews()
        );
    }

    // Getters
    public Long getId() { return id; }
    public String getTitle() { return title; }
    public String getContent() { return content; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public Integer getViews() { return views; }
}
