package com.example.vibeapp.post;

import com.example.vibeapp.post.dto.PostCreateDto;
import com.example.vibeapp.post.dto.PostListDto;
import com.example.vibeapp.post.dto.PostResponseDTO;
import com.example.vibeapp.post.dto.PostUpdateDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Arrays;
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
        int offset = (page - 1) * size;
        return postRepository.findAllPaged(offset, size).stream()
                .map(PostListDto::from)
                .collect(Collectors.toList());
    }

    public int getTotalPages(int size) {
        int totalCount = postRepository.countAll();
        return (int) Math.ceil((double) totalCount / size);
    }

    @Transactional
    public PostResponseDTO getPost(Long id) {
        postRepository.incrementViews(id);
        return toPostResponseDto(findPostById(id));
    }

    @Transactional(readOnly = true)
    public PostResponseDTO getPostDetail(Long id) {
        return toPostResponseDto(findPostById(id));
    }

    @Transactional(readOnly = true)
    public PostUpdateDto getPostForEdit(Long id) {
        Post post = findPostById(id);
        String tags = postTagRepository.findByPostId(id).stream()
                .map(PostTag::getTagName)
                .collect(Collectors.joining(", "));
        return new PostUpdateDto(post.getTitle(), post.getContent(), tags);
    }

    private Post findPostById(Long id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid post id: " + id));
    }

    @Transactional
    public void createPost(PostCreateDto dto) {
        Post post = dto.toEntity();
        post.setCreatedAt(LocalDateTime.now());
        post.setUpdatedAt(null);
        post.setViews(0);
        postRepository.insert(post);
        saveTags(post.getId(), dto.tags());
    }

    @Transactional
    public void updatePost(Long id, PostUpdateDto dto) {
        Post post = findPostById(id);
        post.setTitle(dto.title());
        post.setContent(dto.content());
        post.setUpdatedAt(LocalDateTime.now());
        postRepository.update(post);

        postTagRepository.deleteByPostId(id);
        saveTags(id, dto.tags());
    }

    @Transactional
    public void deletePost(Long id) {
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
                .forEach(tag -> postTagRepository.insert(new PostTag(null, postId, tag)));
    }

    private PostResponseDTO toPostResponseDto(Post post) {
        List<String> tags = postTagRepository.findByPostId(post.getId()).stream()
                .map(PostTag::getTagName)
                .toList();
        return PostResponseDTO.from(post, tags);
    }
}
