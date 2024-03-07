package com.example.blog.service.mapper;

import com.example.blog.domain.Tag;
import com.example.blog.service.dto.TagDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TagMapper {
    TagDTO toDTO(Tag tag);
    Tag toEntity(TagDTO tagDTO);
}
