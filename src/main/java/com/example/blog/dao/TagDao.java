package com.example.blog.dao;

import com.example.blog.dao.repository.TagRepository;
import com.example.blog.domain.QTag;
import com.example.blog.domain.Tag;
import com.querydsl.jpa.impl.JPAQueryFactory;
import io.micrometer.observation.ObservationFilter;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class TagDao {
    private final TagRepository tagRepository;
    private EntityManager entityManager;

    public TagDao(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    public Optional<Tag> findById(Long id){
        return tagRepository.findById(id);
    }

    public boolean existsByName(String name){
        return tagRepository.existsByName(name);
    }

    public Optional<Tag> findByName(String name){
        return tagRepository.findTagByName(name);
    }

    public Tag save(Tag tag){
        return tagRepository.save(tag);
    }


    public Page<Tag> findAll(Pageable pageable){
        return tagRepository.findAll(pageable);
    }

    public void delete(Long id) {
        tagRepository.deleteById(id);
    }


}
