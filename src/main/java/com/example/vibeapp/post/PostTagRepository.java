package com.example.vibeapp.post;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PostTagRepository {

    @PersistenceContext
    private EntityManager em;

    public void insert(PostTag postTag) {
        // persist로 태그 엔티티를 영속화하면 INSERT 대상이 된다.
        em.persist(postTag);
    }

    public void deleteById(Long id) {
        // 기본 키로 조회한 영속 엔티티를 remove한다.
        PostTag postTag = em.find(PostTag.class, id);
        if (postTag != null) {
            em.remove(postTag);
        }
    }

    public List<PostTag> findByPostId(Long postId) {
        // JPQL 조건절로 게시글 ID 기준 태그 목록을 조회한다.
        return em.createQuery(
                        "SELECT pt FROM PostTag pt WHERE pt.postId = :postId ORDER BY pt.id ASC",
                        PostTag.class
                )
                .setParameter("postId", postId)
                .getResultList();
    }

    public void deleteByPostId(Long postId) {
        // 벌크 삭제 JPQL은 조건에 맞는 태그를 한 번에 삭제한다.
        em.createQuery("DELETE FROM PostTag pt WHERE pt.postId = :postId")
                .setParameter("postId", postId)
                .executeUpdate();
    }
}
