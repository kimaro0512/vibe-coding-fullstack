package com.example.vibeapp.post;

import com.example.vibeapp.post.dto.PostCreateDto;
import com.example.vibeapp.post.dto.PostListDto;
import com.example.vibeapp.post.dto.PostResponseDTO;
import com.example.vibeapp.post.dto.PostUpdateDto;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostService {
    private final PostRepository postRepository;
    private final PostTagRepository postTagRepository;

    public PostService(PostRepository postRepository, PostTagRepository postTagRepository) {
        this.postRepository = postRepository;
        this.postTagRepository = postTagRepository;
    }

    public List<PostListDto> getPosts(int page, int size) {
        PageRequest pageable = PageRequest.of(page - 1, size);
        return postRepository.findByOrderByIdDesc(pageable).stream()
                .map(PostListDto::from)
                .collect(Collectors.toList());
    }

    public int getTotalPages(int size) {
        long totalCount = postRepository.count();
        return (int) Math.ceil((double) totalCount / size);
    }

    @Transactional(readOnly = true)
    public PostResponseDTO getPost(Long id) {
        return toPostResponseDto(findPostById(id));
    }

    private Post findPostById(Long id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid post id: " + id));
    }

    @Transactional
    public PostResponseDTO createPost(PostCreateDto dto) {
        Post post = dto.toEntity();
        post.setCreatedAt(LocalDateTime.now());
        post.setUpdatedAt(null);
        post.setViews(0);
        Post savedPost = postRepository.save(post);
        saveTags(savedPost.getId(), dto.tags());
        return toPostResponseDto(savedPost);
    }

    @Transactional
    public PostResponseDTO updatePost(Long id, PostUpdateDto dto) {
        Post post = findPostById(id);
        post.setTitle(dto.title());
        post.setContent(dto.content());
        post.setUpdatedAt(LocalDateTime.now());

        postTagRepository.deleteByPostId(id);
        saveTags(id, dto.tags());

        return toPostResponseDto(post);
    }

    @Transactional
    public void deletePost(Long id) {
        findPostById(id);
        postTagRepository.deleteByPostId(id);
        postRepository.deleteById(id);
    }

    private void saveTags(Long postId, String rawTags) {
        if (rawTags == null || rawTags.isBlank()) {
            return;
        }

        Arrays.stream(rawTags.split(","))
                .map(String::trim)
                .filter(tag -> !tag.isBlank())
                .distinct()
                .forEach(tag -> postTagRepository.save(new PostTag(null, postId, tag)));
    }

    private PostResponseDTO toPostResponseDto(Post post) {
        List<String> tags = postTagRepository.findByPostIdOrderByIdAsc(post.getId()).stream()
                .map(PostTag::getTagName)
                .toList();
        return PostResponseDTO.from(post, tags);
    }
}
