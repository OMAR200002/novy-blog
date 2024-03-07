package com.example.blog.service.dto;

import com.example.blog.domain.Tag;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostDTO {
    private Long id;

    @NotBlank(message = "Post title is mandatory")
    @Size(max=256,message = "Post title is to long (max 256 character)")
    private String title;

    @NotBlank(message = "Post image url is mandatory")
    @Size(max=256,message = "Post image url is to long (max 256 character)")
    private String imageUrl;

    @Size(max=256,message = "Post description is to long (max 256 character)")
    private String description;

    @NotBlank(message = "Post content is mandatory")
    private String content;

    private Date date;

    private Long blogId;

    private Set<TagDTO> tags = new HashSet<>();
}
