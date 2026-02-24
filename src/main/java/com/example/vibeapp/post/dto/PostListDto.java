package com.example.vibeapp.post.dto;

import com.example.vibeapp.post.Post;
import java.time.LocalDateTime;

public class PostListDto {
    private Long id;
    private String title;
    private LocalDateTime createdAt;
    private Integer views;

    public PostListDto() {}

    public PostListDto(Long id, String title, LocalDateTime createdAt, Integer views) {
        this.id = id;
        this.title = title;
        this.createdAt = createdAt;
        this.views = views;
    }

    public static PostListDto from(Post entity) {
        return new PostListDto(
            entity.getId(),
            entity.getTitle(),
            entity.getCreatedAt(),
            entity.getViews()
        );
    }

    // Getters
    public Long getId() { return id; }
    public String getTitle() { return title; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public Integer getViews() { return views; }
}
