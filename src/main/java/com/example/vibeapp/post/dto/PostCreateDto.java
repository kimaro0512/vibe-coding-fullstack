package com.example.vibeapp.post.dto;

import com.example.vibeapp.post.Post;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(name = "PostCreateDto", description = "게시글 생성 요청")
public record PostCreateDto(
    @Schema(description = "게시글 제목", example = "Spring Data JPA 전환기", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "제목은 필수입니다.")
    @Size(max = 100, message = "제목은 100자를 초과할 수 없습니다.")
    String title,

    @Schema(description = "게시글 내용", example = "오늘은 순수 JPA에서 Spring Data JPA로 전환했다.", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "내용은 필수입니다.")
    String content,

    @Schema(description = "쉼표로 구분된 태그", example = "spring, jpa, refactoring", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    String tags
) {
    public PostCreateDto() {
        this("", "", "");
    }

    public Post toEntity() {
        Post post = new Post();
        post.setTitle(this.title);
        post.setContent(this.content);
        return post;
    }
}
