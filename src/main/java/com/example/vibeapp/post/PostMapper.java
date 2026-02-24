package com.example.vibeapp.post;

import org.apache.ibatis.annotations.Mapper;
import java.util.List;
import java.util.Optional;

@Mapper
public interface PostMapper {
    List<Post> findAll();
    Optional<Post> findById(Long id);
    void insert(Post post);
    void update(Post post);
    void deleteById(Long id);
    void incrementViews(Long id);
}
