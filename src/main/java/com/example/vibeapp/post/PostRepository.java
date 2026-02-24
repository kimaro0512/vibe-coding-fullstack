package com.example.vibeapp.post;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {

    // ID 내림차순으로 게시글 목록을 페이지 단위로 조회한다.
    Page<Post> findByOrderByIdDesc(Pageable pageable);
}
