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

    public List<PostListDto> getPosts(int page, int size) {
        int offset = (page - 1) * size;
        return postRepository.findAllPaged(offset, size).stream()
                .map(PostListDto::from)
                .collect(Collectors.toList());
    }

    public int getTotalPages(int size) {
        int totalCount = postRepository.countAll();
        return (int) Math.ceil((double) totalCount / size);
    }

    public PostResponseDTO getPost(Long id) {
        postRepository.incrementViews(id);
        return PostResponseDTO.from(findPostById(id));
    }

    public PostResponseDTO getPostDetail(Long id) {
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
        postRepository.insert(post);
    }

    public void updatePost(Long id, PostUpdateDto dto) {
        Post post = findPostById(id);
        post.setTitle(dto.title());
        post.setContent(dto.content());
        post.setUpdatedAt(LocalDateTime.now());
        postRepository.update(post);
    }

    public void deletePost(Long id) {
        postRepository.deleteById(id);
    }
}
