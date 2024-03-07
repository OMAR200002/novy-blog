package com.example.blog.dao.repository;

import com.example.blog.domain.Blog;
import com.example.blog.domain.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post,Long> {
    boolean existsById(Long id);
    Optional<Post> findById(Long aLong);
    Page<Post> findAll(Pageable pageable);
    boolean existsByTitle(String title);
    @Query("SELECT p FROM Post p ORDER BY p.date DESC LIMIT 6")
    List<Post> getLatestPosts();

    Page<Post> findByTitleContaining(String searchTerm,Pageable pageable);

    //@Query("SELECT p FROM Post p JOIN p.tags t WHERE p.title LIKE %:searchTerm% AND t.name IN :tagNames")
    //Page<Post> findPostsFiltred(String searchTerm, @Param("tagNames") List<String> tags, Pageable pageable);

}
