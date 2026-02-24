package com.example.vibeapp.post;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;
import java.util.Optional;

@Mapper
public interface PostRepository {
    List<Post> findAll();
    
    // 페이징 처리를 위한 메서드
    List<Post> findAllPaged(@Param("offset") int offset, @Param("limit") int limit);
    
    int countAll();

    Optional<Post> findById(Long id);

    void insert(Post post);

    void update(Post post);

    void deleteById(Long id);

    void incrementViews(Long id);
}
