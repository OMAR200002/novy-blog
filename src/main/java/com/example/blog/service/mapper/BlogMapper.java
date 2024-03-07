package com.example.blog.service.mapper;

import com.example.blog.domain.Blog;
import com.example.blog.domain.User;
import com.example.blog.service.dto.BlogDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface BlogMapper {
    BlogDTO toDTO(Blog blog);

    Blog toEntity(BlogDTO userDTO);

}
