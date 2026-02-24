package com.example.vibeapp.post;

import com.example.vibeapp.post.dto.PostCreateDto;
import com.example.vibeapp.post.dto.PostListDto;
import com.example.vibeapp.post.dto.PostResponseDTO;
import com.example.vibeapp.post.dto.PostUpdateDto;
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
public class PostController {
    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping
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
    public ResponseEntity<PostResponseDTO> detail(@PathVariable("no") Long no) {
        return ResponseEntity.ok(postService.getPost(no));
    }

    @PostMapping
    public ResponseEntity<PostResponseDTO> create(@Valid @RequestBody PostCreateDto request) {
        PostResponseDTO created = postService.createPost(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PatchMapping("/{no}")
    public ResponseEntity<PostResponseDTO> update(
            @PathVariable("no") Long no,
            @Valid @RequestBody PostUpdateDto request
    ) {
        return ResponseEntity.ok(postService.updatePost(no, request));
    }

    @DeleteMapping("/{no}")
    public ResponseEntity<Void> delete(@PathVariable("no") Long no) {
        postService.deletePost(no);
        return ResponseEntity.noContent().build();
    }

    public record PostListResponse(
            List<PostListDto> posts,
            int page,
            int size,
            int totalPages
    ) {}
}
