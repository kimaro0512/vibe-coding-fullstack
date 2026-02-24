package com.example.vibeapp.repository;

import com.example.vibeapp.domain.Post;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class PostRepository {
    private final List<Post> posts = new ArrayList<>();
    private final AtomicLong nextId = new AtomicLong(1);

    public List<Post> findAll() {
        return posts.stream()
                .sorted((p1, p2) -> p2.getNo().compareTo(p1.getNo()))
                .toList();
    }

    public Post save(Post post) {
        if (post.getNo() == null) {
            post.setNo(nextId.getAndIncrement());
        }
        posts.add(post);
        return post;
    }

    public Optional<Post> findById(Long no) {
        return posts.stream().filter(p -> p.getNo().equals(no)).findFirst();
    }

    public void incrementViews(Long no) {
        findById(no).ifPresent(post -> post.setViews(post.getViews() + 1));
    }

    public void deleteById(Long no) {
        posts.removeIf(post -> post.getNo().equals(no));
    }
}
