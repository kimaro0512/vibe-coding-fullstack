package com.example.vibeapp.post;

import com.example.vibeapp.global.ErrorResponse;
import com.example.vibeapp.global.openapi.CommonErrorResponses;
import com.example.vibeapp.post.dto.PostCreateDto;
import com.example.vibeapp.post.dto.PostListDto;
import com.example.vibeapp.post.dto.PostResponseDTO;
import com.example.vibeapp.post.dto.PostUpdateDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
@Tag(name = "Posts", description = "게시글 API")
public class PostController {
    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping
    @Operation(
            summary = "게시글 목록 조회",
            description = "페이지 기반으로 게시글 목록을 조회합니다.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "목록 조회 성공",
                    content = @Content(schema = @Schema(implementation = PostListResponse.class))
            )
    })
    @CommonErrorResponses
    public ResponseEntity<PostListResponse> list(
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "5") int size
    ) {
        int normalizedPage = Math.max(page, 1);
        int normalizedSize = Math.max(size, 1);
        List<PostListDto> posts = postService.getPosts(normalizedPage, normalizedSize);
        int totalPages = postService.getTotalPages(normalizedSize);
        return ResponseEntity.ok(new PostListResponse(posts, normalizedPage, normalizedSize, totalPages));
    }

    @GetMapping("/{no}")
    @Operation(
            summary = "게시글 상세 조회",
            description = "게시글 번호로 상세 정보를 조회합니다.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "상세 조회 성공",
                    content = @Content(schema = @Schema(implementation = PostResponseDTO.class))
            )
    })
    @CommonErrorResponses
    public ResponseEntity<PostResponseDTO> detail(@PathVariable("no") Long no) {
        return ResponseEntity.ok(postService.getPost(no));
    }

    @PostMapping
    @Operation(
            summary = "게시글 등록",
            description = "새 게시글을 등록합니다.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "등록 성공",
                    content = @Content(schema = @Schema(implementation = PostResponseDTO.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "요청 검증 실패",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            )
    })
    @CommonErrorResponses
    public ResponseEntity<PostResponseDTO> create(@Valid @RequestBody PostCreateDto request) {
        PostResponseDTO created = postService.createPost(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PatchMapping("/{no}")
    @Operation(
            summary = "게시글 수정",
            description = "게시글 번호에 해당하는 게시글을 수정합니다.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "수정 성공",
                    content = @Content(schema = @Schema(implementation = PostResponseDTO.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "요청 검증 실패",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            )
    })
    @CommonErrorResponses
    public ResponseEntity<PostResponseDTO> update(
            @PathVariable("no") Long no,
            @Valid @RequestBody PostUpdateDto request
    ) {
        return ResponseEntity.ok(postService.updatePost(no, request));
    }

    @DeleteMapping("/{no}")
    @Operation(
            summary = "게시글 삭제",
            description = "게시글 번호에 해당하는 게시글을 삭제합니다.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "삭제 성공")
    })
    @CommonErrorResponses
    public ResponseEntity<Void> delete(@PathVariable("no") Long no) {
        postService.deletePost(no);
        return ResponseEntity.noContent().build();
    }

    @Schema(name = "PostListResponse", description = "게시글 목록 조회 응답")
    public record PostListResponse(
            @Schema(description = "게시글 목록")
            List<PostListDto> posts,
            @Schema(description = "현재 페이지", example = "1")
            int page,
            @Schema(description = "페이지 크기", example = "5")
            int size,
            @Schema(description = "전체 페이지 수", example = "3")
            int totalPages
    ) {}
}
