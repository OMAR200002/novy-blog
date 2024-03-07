package com.example.blog.dao.repository;

import com.example.blog.domain.Blog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BlogRepository extends JpaRepository<Blog,Long> {
    Optional<Blog> findByName(String name);
    boolean existsById(Long id);
    Page<Blog> findAll(Pageable pageable);

    void deleteById(Long id);

}
