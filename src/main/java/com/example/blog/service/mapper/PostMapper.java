package com.example.blog.service.mapper;

import com.example.blog.domain.Post;
import com.example.blog.service.dto.PostDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PostMapper {
    @Mapping(source = "blog.id" , target = "blogId")
    PostDTO toDTO(Post post);
    Post toEntity(PostDTO postDTO);
}
