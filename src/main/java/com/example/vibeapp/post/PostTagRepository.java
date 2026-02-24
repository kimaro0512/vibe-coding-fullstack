package com.example.vibeapp.post;

import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface PostTagRepository {

    /**
     * 태그를 POST_TAGS 테이블에 추가합니다.
     *
     * @param postTag 추가할 PostTag 엔티티
     */
    void insert(PostTag postTag);

    /**
     * ID로 태그를 POST_TAGS 테이블에서 삭제합니다.
     *
     * @param id 삭제할 태그의 ID
     */
    void deleteById(Long id);

    List<PostTag> findByPostId(Long postId);

    void deleteByPostId(Long postId);
}
