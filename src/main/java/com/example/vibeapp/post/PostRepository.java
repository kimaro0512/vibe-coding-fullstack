package com.example.vibeapp.post;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class PostRepository {

    @PersistenceContext
    private EntityManager em;

    public List<Post> findAll() {
        // JPQL은 엔티티(Post) 기준으로 조회한다.
        return em.createQuery("SELECT p FROM Post p ORDER BY p.id DESC", Post.class)
                .getResultList();
    }

    public List<Post> findAllPaged(int offset, int limit) {
        // setFirstResult/setMaxResults로 DB 페이징을 적용한다.
        return em.createQuery("SELECT p FROM Post p ORDER BY p.id DESC", Post.class)
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();
    }

    public int countAll() {
        // JPQL COUNT 결과(Long)를 애플리케이션에서 int로 변환한다.
        Long count = em.createQuery("SELECT COUNT(p) FROM Post p", Long.class)
                .getSingleResult();
        return count.intValue();
    }

    public Optional<Post> findById(Long id) {
        // 기본 키 조회는 em.find로 영속성 컨텍스트를 우선 확인한다.
        return Optional.ofNullable(em.find(Post.class, id));
    }

    public void insert(Post post) {
        // persist 호출 시 엔티티가 영속 상태가 되며 INSERT 대상이 된다.
        em.persist(post);
    }

    public void update(Post post) {
        // merge는 준영속/비영속 엔티티 상태를 영속성 컨텍스트에 반영한다.
        em.merge(post);
    }

    public void deleteById(Long id) {
        // 영속 엔티티 remove로 DELETE를 수행한다.
        findById(id).ifPresent(em::remove);
    }

    public void incrementViews(Long id) {
        // 영속 엔티티의 필드 변경은 트랜잭션 커밋 시 Dirty Checking으로 반영된다.
        findById(id).ifPresent(post -> post.setViews(post.getViews() + 1));
    }
}
