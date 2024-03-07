package com.example.blog.dao;

import com.example.blog.dao.repository.BlogRepository;
import com.example.blog.domain.Blog;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class BlogDao {
    private final BlogRepository blogRepository;

    public Page<Blog> findAll(Pageable pageable){
        return blogRepository.findAll(pageable);
    }
    public Optional<Blog> findById(Long id){
        return blogRepository.findById(id);
    }

    public Optional<Blog> findByName(String name){
        return blogRepository.findByName(name);
    }

    public boolean existsById(Long id){
        return blogRepository.existsById(id);
    }

//    public Optional<Blog> lazyLoadById(Long id){
//        return existsById(id)?blogRepository.getReferenceById(id):Optional.ofNullable(null);
//    }

    public Blog save(Blog blog){
        return blogRepository.save(blog);
    }

    public void delete(Long id){
        blogRepository.deleteById(id);
    }
}
