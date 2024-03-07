package com.example.blog.dao;

import com.example.blog.dao.repository.CommentRepository;
import com.example.blog.domain.Comment;
import com.example.blog.domain.Post;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional
@AllArgsConstructor
public class CommentDao {
    private final CommentRepository commentRepository;

    public Comment save(Comment comment) {
        return commentRepository.save(comment);
    }

    public Optional<Comment> findById(Long id) {
        return commentRepository.findById(id);
    }
    public Page<Comment> findAllByPost(Post post, Pageable pageable){
        return commentRepository.findAllByPost(post,pageable);
    }
}
