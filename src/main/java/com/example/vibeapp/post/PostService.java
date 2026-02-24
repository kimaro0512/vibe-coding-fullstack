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
    private final PostMapper postMapper;

    public PostService(PostMapper postMapper) {
        this.postMapper = postMapper;
    }

    public List<PostListDto> getPosts(int page, int size) {
        List<Post> allPosts = postMapper.findAll();
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
        int totalCount = postMapper.findAll().size();
        return (int) Math.ceil((double) totalCount / size);
    }

    public PostResponseDTO getPost(Long id) {
        postMapper.incrementViews(id);
        return PostResponseDTO.from(findPostById(id));
    }

    public PostUpdateDto getPostForEdit(Long id) {
        Post post = findPostById(id);
        return new PostUpdateDto(post.getTitle(), post.getContent());
    }

    private Post findPostById(Long id) {
        return postMapper.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid post id: " + id));
    }

    public void createPost(PostCreateDto dto) {
        Post post = dto.toEntity();
        post.setCreatedAt(LocalDateTime.now());
        post.setUpdatedAt(null);
        post.setViews(0);
        postMapper.insert(post);
    }

    public void updatePost(Long id, PostUpdateDto dto) {
        Post post = findPostById(id);
        post.setTitle(dto.title());
        post.setContent(dto.content());
        post.setUpdatedAt(LocalDateTime.now());
        postMapper.update(post);
    }

    public void deletePost(Long id) {
        postMapper.deleteById(id);
    }
}
