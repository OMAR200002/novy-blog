package com.example.blog.service.dto.comment;

import com.example.blog.domain.Post;
import com.example.blog.domain.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;


@Data
@AllArgsConstructor
public class CommentDTO {

    private Long id;

    @NotBlank(message = "Comment is mandatory")
    private String content;

    @NotNull(message = "Comment Date can't be null")
    private Date datePosted;

    private Long postId;

    private Long authorId;
}