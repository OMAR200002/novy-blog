package com.example.blog.service;

import com.example.blog.dao.CommentDao;
import com.example.blog.dao.PostDao;
import com.example.blog.dao.UserDao;
import com.example.blog.domain.Comment;
import com.example.blog.domain.Post;
import com.example.blog.domain.User;
import com.example.blog.service.dto.comment.CommentDTO;
import com.example.blog.service.dto.comment.CommentResponseDTO;
import com.example.blog.service.exception.BusinessException;
import com.example.blog.service.mapper.CommentMapper;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@AllArgsConstructor
public class CommentService {
    private final CommentDao commentDao;
    private final PostDao postDao;
    private final UserDao userDao;
    private final CommentMapper commentMapper;


    public CommentDTO creat(CommentDTO commentDTO) {
        Comment comment = commentMapper.toEntity(commentDTO);
        Post post = postDao.findById(commentDTO.getPostId()).orElseThrow(() -> new BusinessException(HttpStatus.NOT_FOUND.value(), "post.not.found", "Post not found"));
        comment.setPost(post);
        User author = userDao.findById(commentDTO.getAuthorId()).orElseThrow(() -> new BusinessException(HttpStatus.NOT_FOUND.value(), "user.not.found", "User not found"));
        comment.setAuthor(author);
        return commentMapper.toDTO(commentDao.save(comment));
    }

    public CommentResponseDTO get(Long id) {
        Comment comment = commentDao.findById(id).orElseThrow(() -> new BusinessException(HttpStatus.NOT_FOUND.value(), "comment.not.found", "Comment not found"));
        return commentMapper.toResponseDTO(comment);
    }

    public Page<CommentResponseDTO> getCommentsByPost(Long postId,int pageNumber,int pageSize){
        Post post = postDao.findById(postId).orElseThrow(() -> new BusinessException(HttpStatus.NOT_FOUND.value(), "post.not.found", "Post not found"));
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Comment> allByPost = commentDao.findAllByPost(post, pageable);

        return allByPost.map(commentMapper::toResponseDTO);
    }
}
