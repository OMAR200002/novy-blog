package com.example.blog.service.dto.comment;

import com.example.blog.service.dto.user.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class CommentResponseDTO {

    private Long id;

    private String content;

    private Date datePosted;

    private Long postId;

    private UserDTO userDTO;
}
