package com.example.vibeapp.post.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(name = "PostUpdateDto", description = "게시글 수정 요청")
public record PostUpdateDto(
    @Schema(description = "게시글 제목", example = "Spring Data JPA 전환 완료", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "제목은 필수입니다.")
    @Size(max = 100, message = "제목은 100자를 초과할 수 없습니다.")
    String title,

    @Schema(description = "게시글 내용", example = "리팩토링 후 코드량이 많이 줄었다.", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "내용은 필수입니다.")
    String content,

    @Schema(description = "쉼표로 구분된 태그", example = "spring, openapi", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    String tags
) {
    public PostUpdateDto() {
        this("", "", "");
    }
}
