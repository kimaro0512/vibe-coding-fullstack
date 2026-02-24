package com.example.vibeapp.post;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostTagRepository extends JpaRepository<PostTag, Long> {

    // 특정 게시글 ID에 연결된 태그를 ID 오름차순으로 조회한다.
    List<PostTag> findByPostIdOrderByIdAsc(Long postId);

    // 특정 게시글 ID에 연결된 태그를 한 번에 삭제한다.
    void deleteByPostId(Long postId);
}
