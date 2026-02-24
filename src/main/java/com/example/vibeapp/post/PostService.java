package com.example.vibeapp.post;

import com.example.vibeapp.post.dto.PostCreateDto;
import com.example.vibeapp.post.dto.PostListDto;
import com.example.vibeapp.post.dto.PostResponseDTO;
import com.example.vibeapp.post.dto.PostUpdateDto;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostService {
    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @PostConstruct
    public void init() {
        for (int i = 1; i <= 10; i++) {
            postRepository.save(new Post(
                (long) i,
                "테스트 게시글 제목 " + i,
                "이것은 " + i + "번째 테스트 게시글 내용입니다.",
                LocalDateTime.now().minusDays(10 - i),
                i * 10
            ));
        }
    }

    public List<PostListDto> getPosts(int page, int size) {
        List<Post> allPosts = postRepository.findAll();
        int totalCount = allPosts.size();
        
        int fromIndex = (page - 1) * size;
        if (fromIndex >= totalCount) {
            return List.of();
        }
        
        int toIndex = Math.min(fromIndex + size, totalCount);
        return allPosts.subList(fromIndex, toIndex).stream()
                .map(PostListDto::from)
                .collect(Collectors.toList());
    }

    public int getTotalPages(int size) {
        int totalCount = postRepository.findAll().size();
        return (int) Math.ceil((double) totalCount / size);
    }

    public PostResponseDTO getPost(Long id) {
        postRepository.incrementViews(id);
        return PostResponseDTO.from(findPostById(id));
    }

    public PostUpdateDto getPostForEdit(Long id) {
        Post post = findPostById(id);
        return new PostUpdateDto(post.getTitle(), post.getContent());
    }

    private Post findPostById(Long id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid post id: " + id));
    }

    public void createPost(PostCreateDto dto) {
        Post post = dto.toEntity();
        post.setCreatedAt(LocalDateTime.now());
        post.setUpdatedAt(null);
        post.setViews(0);
        postRepository.save(post);
    }

    public void updatePost(Long id, PostUpdateDto dto) {
        Post post = findPostById(id);
        post.setTitle(dto.title());
        post.setContent(dto.content());
        post.setUpdatedAt(LocalDateTime.now());
    }

    public void deletePost(Long id) {
        postRepository.deleteById(id);
    }
}
