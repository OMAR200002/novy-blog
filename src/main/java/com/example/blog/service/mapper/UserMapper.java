package com.example.blog.service.mapper;

import com.example.blog.domain.User;
import com.example.blog.service.dto.user.UserDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {
    UserDTO toDTO(User user);
    List<UserDTO> toListDTOs(List<User> users);

    User toEntity(UserDTO userDTO);
    List<User> toListEntity(List<UserDTO> userDTOs);
}
