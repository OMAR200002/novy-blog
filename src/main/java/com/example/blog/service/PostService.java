package com.example.blog.service;

import com.example.blog.dao.BlogDao;
import com.example.blog.dao.PostDao;
import com.example.blog.dao.TagDao;
import com.example.blog.dao.filters.PostFilter;
import com.example.blog.dao.repository.PostRepository;
import com.example.blog.domain.Blog;
import com.example.blog.domain.Post;
import com.example.blog.domain.Tag;
import com.example.blog.service.dto.PostDTO;
import com.example.blog.service.exception.BusinessException;
import com.example.blog.service.mapper.PostMapper;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
public class PostService {

    private final PostDao postDao;
    private final BlogDao blogDao;
    private final TagDao tagDao;
    private final PostRepository postRepository;
    private final PostMapper postMapper;


    public PostDTO create(PostDTO postDTO){
        if(postDao.existsByTitle(postDTO.getTitle())){
            throw new BusinessException(HttpStatus.NOT_FOUND.value(), "post.with.title.already.exists", "Post with title already exists");
        }

        Blog blog = blogDao
                .findById(postDTO.getBlogId())
                .orElseThrow(() -> new BusinessException(HttpStatus.NOT_FOUND.value(), "blog.not.found", "Blog not found"));

        Set<Tag> tags = postDTO
                .getTags()
                .stream()
                .map(tagDTO -> {
                    Optional<Tag> tagByName = tagDao.findByName(tagDTO.getName());
                    //create tag if doesn't exists
                    return tagByName.orElseGet(() -> tagDao.save(new Tag(0L, tagDTO.getName())));
                }).collect(Collectors.toSet());

        Post newPost = postMapper.toEntity(postDTO);

        newPost.setBlog(blog);
        newPost.setTags(tags);

        Post savedPost = postDao.save(newPost);

        postDTO.setId(savedPost.getId());

        return postDTO;
    }
    public PostDTO update(PostDTO postDTO,Long id){
        Post postDB = postRepository.findById(id).orElseThrow(() -> new BusinessException(HttpStatus.NOT_FOUND.value(), "post.not.found", "Post not found"));

        Blog blog = blogDao.findById(postDTO.getBlogId()).orElseThrow(() -> new BusinessException(HttpStatus.NOT_FOUND.value(), "blog.not.found", "Blog not found"));

        Set<Tag> newTags = postDTO
                .getTags()
                .stream()
                .map(tagDTO -> {
                    Optional<Tag> tagByName = tagDao.findByName(tagDTO.getName());
                    return tagByName.orElseGet(() -> tagDao.save(new Tag(0L, tagDTO.getName())));
                }).collect(Collectors.toSet());

        postDB.setTitle(postDB.getTitle());
        postDB.setContent(postDTO.getTitle());
        postDB.setDate(postDTO.getDate());
        postDB.setBlog(blog);
        Set<Tag> tags = postDB.getTags();
        tags.clear();
        tags.addAll(newTags);


        postDTO.setId(id);
        return postDTO;
    }



    public Page<PostDTO> getAllFilteredWith(PostFilter postFilter) {
        return postDao.findALlFilteredWith(postFilter).map(postMapper::toDTO);
    }

    public PostDTO getById(Long id) {
        Post post = postDao.findById(id).orElseThrow(() -> new BusinessException(HttpStatus.NOT_FOUND.value(), "post.not.found", "Post not found"));
        return postMapper.toDTO(post);
    }

    public void delete(Long id) {
        postDao.delete(id);
    }

    public List<PostDTO> getLatestPosts() {
        List<Post> latestPosts = postDao.getLatestPosts();
        return latestPosts.stream().map(postMapper::toDTO).toList();
    }
}
