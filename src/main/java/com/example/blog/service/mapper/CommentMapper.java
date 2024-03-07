package com.example.blog.service.mapper;

import com.example.blog.domain.Comment;
import com.example.blog.service.dto.comment.CommentDTO;
import com.example.blog.service.dto.comment.CommentResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CommentMapper {
    @Mapping(source = "post.id" , target = "postId")
    @Mapping(source = "author.id" , target = "authorId")
    CommentDTO toDTO(Comment comment);
    @Mapping(source = "post.id" , target = "postId")
    @Mapping(source = "author.id" , target = "userDTO.id")
    @Mapping(source = "author.imageUrl" , target = "userDTO.imageUrl")
    @Mapping(source = "author.username" , target = "userDTO.login")
    CommentResponseDTO toResponseDTO(Comment comment);
    Comment toEntity(CommentDTO commentDTO);
}
