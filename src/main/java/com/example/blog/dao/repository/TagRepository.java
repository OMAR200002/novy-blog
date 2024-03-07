package com.example.blog.dao.repository;

import com.example.blog.domain.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface TagRepository extends JpaRepository<Tag,Long> {
    boolean existsByName(String name);
    Optional<Tag> findTagByName(String name);
    List<Tag> findAllByName(String name);
    Page<Tag> findAll(Pageable pageable);
}
