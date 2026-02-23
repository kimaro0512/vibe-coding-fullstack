package com.example.vibeapp.domain;

import java.time.LocalDateTime;

public class Post {
    private Long no;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Integer views;

    public Post() {}

    public Post(Long no, String title, String content, LocalDateTime createdAt, Integer views) {
        this.no = no;
        this.title = title;
        this.content = content;
        this.createdAt = createdAt;
        this.updatedAt = createdAt;
        this.views = views;
    }

    // Getters and Setters
    public Long getNo() { return no; }
    public void setNo(Long no) { this.no = no; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    public Integer getViews() { return views; }
    public void setViews(Integer views) { this.views = views; }
}
