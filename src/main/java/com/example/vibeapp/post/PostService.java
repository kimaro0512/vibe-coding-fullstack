package com.example.vibeapp.post;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

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

    public List<Post> getPosts(int page, int size) {
        List<Post> allPosts = postRepository.findAll();
        int totalCount = allPosts.size();
        
        int fromIndex = (page - 1) * size;
        if (fromIndex >= totalCount) {
            return List.of();
        }
        
        int toIndex = Math.min(fromIndex + size, totalCount);
        return allPosts.subList(fromIndex, toIndex);
    }

    public int getTotalPages(int size) {
        int totalCount = postRepository.findAll().size();
        return (int) Math.ceil((double) totalCount / size);
    }

    public Post getPost(Long no) {
        postRepository.incrementViews(no);
        return findPostById(no);
    }

    public Post getPostForEdit(Long no) {
        return findPostById(no);
    }

    private Post findPostById(Long no) {
        return postRepository.findById(no)
                .orElseThrow(() -> new IllegalArgumentException("Invalid post number: " + no));
    }

    public void addPost(Post post) {
        post.setCreatedAt(LocalDateTime.now());
        post.setUpdatedAt(null);
        post.setViews(0);
        postRepository.save(post);
    }

    public void updatePost(Long no, String title, String content) {
        Post post = findPostById(no);
        post.setTitle(title);
        post.setContent(content);
        post.setUpdatedAt(LocalDateTime.now());
    }

    public void deletePost(Long no) {
        postRepository.deleteById(no);
    }
}
