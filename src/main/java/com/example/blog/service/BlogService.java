package com.example.blog.service;

import com.example.blog.dao.BlogDao;
import com.example.blog.dao.UserDao;
import com.example.blog.domain.Blog;
import com.example.blog.domain.User;
import com.example.blog.service.dto.BlogDTO;
import com.example.blog.service.exception.BusinessException;
import com.example.blog.service.mapper.BlogMapper;
import com.example.blog.service.mapper.UserMapper;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class BlogService {
    private final BlogDao blogDao;
    private final UserService userService;
    private final BlogMapper blogMapper;
    private final UserMapper userMapper;

    public BlogService(BlogDao blogDao, UserDao userDao, UserService userService, BlogMapper blogMapper, UserMapper userMapper) {
        this.blogDao = blogDao;
        this.userService = userService;
        this.blogMapper = blogMapper;
        this.userMapper = userMapper;
    }

    public Page<BlogDTO> getAll(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return blogDao.findAll(pageable).map(blogMapper::toDTO);
    }

    public BlogDTO get(Long id){
        Blog blog = blogDao.findById(id).orElseThrow(() -> new BusinessException(HttpStatus.NOT_FOUND.value(),"blog.not.found","Blog not found"));
        return blogMapper.toDTO(blog);
    }

    public BlogDTO create(BlogDTO blogDTO){
        Blog blog = blogMapper.toEntity(blogDTO);
        Blog savedBlog = blogDao.save(blog);
        return blogMapper.toDTO(savedBlog);
    }

    public BlogDTO update(BlogDTO blogDTO,Long id){
        if (id == null) {
            throw new BusinessException(HttpStatus.BAD_REQUEST.value(),"invalid.id","Invalid id");
        }
        if (!blogDao.existsById(id)) {

 throw new BusinessException(HttpStatus.NOT_FOUND.value(),"blog.not.found.id","Blog not found");
        }
        Blog blog = blogMapper.toEntity(blogDTO);

        Blog updatedBlog = blogDao.save(blog);

        return blogMapper.toDTO(updatedBlog);
    }

    public void delete(Long id){
        get(id);
        blogDao.delete(id);
    }
}
