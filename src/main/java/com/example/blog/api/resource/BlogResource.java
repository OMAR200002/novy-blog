package com.example.blog.api.resource;

import com.example.blog.dao.repository.BlogRepository;
import com.example.blog.domain.Blog;
import com.example.blog.service.BlogService;
import com.example.blog.service.dto.BlogDTO;
import com.example.blog.service.exception.BusinessException;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/api/blogs")
@AllArgsConstructor
public class BlogResource {
    private final BlogService blogService;


    @GetMapping("/{id}")
    public ResponseEntity<BlogDTO> get(@PathVariable("id") Long id) {
        return ResponseEntity.ok(blogService.get(id));
    }

    @GetMapping("")
    public ResponseEntity<Page<BlogDTO>> getAll(@RequestParam(defaultValue = "0") int pageNumber,
                                  @RequestParam(defaultValue = "10") int pageSize){
        Page<BlogDTO> allBlogs = blogService.getAll(pageNumber, pageSize);
        return ResponseEntity.ok(allBlogs);
    }

    @PostMapping("")
    public ResponseEntity<BlogDTO> create(@Valid @RequestBody BlogDTO blogDTO) throws URISyntaxException {
        BlogDTO result = blogService.create(blogDTO);
        return ResponseEntity
                .created(new URI("/api/blogs/" + result.getId()))
                .body(result);
    }


    @PutMapping("/{id}")
    public ResponseEntity<BlogDTO> updateBlog(@PathVariable(value = "id") final Long id, @Valid @RequestBody BlogDTO blogDTO)
           {
        BlogDTO result = blogService.update(blogDTO, id);
        return ResponseEntity
                .ok()
                .body(result);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBlog(@PathVariable("id") Long id) {
        blogService.delete(id);
        return ResponseEntity
                .noContent()
                .build();
    }


}
