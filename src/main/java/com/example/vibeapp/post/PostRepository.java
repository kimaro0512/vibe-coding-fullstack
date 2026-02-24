package com.example.vibeapp.post;
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
                .sorted((p1, p2) -> p2.getId().compareTo(p1.getId()))
                .toList();
    }

    public Post save(Post post) {
        if (post.getId() == null) {
            post.setId(nextId.getAndIncrement());
        } else {
            // 수동으로 ID가 지정된 경우 nextId를 업데이트하여 중복 방지
            nextId.set(Math.max(nextId.get(), post.getId() + 1));
        }
        posts.add(post);
        return post;
    }

    public Optional<Post> findById(Long id) {
        return posts.stream().filter(p -> p.getId().equals(id)).findFirst();
    }

    public void incrementViews(Long id) {
        findById(id).ifPresent(post -> post.setViews(post.getViews() + 1));
    }

    public void deleteById(Long id) {
        posts.removeIf(post -> post.getId().equals(id));
    }
}
