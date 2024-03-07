package com.example.blog.api.resource;

import com.example.blog.dao.filters.PostFilter;
import com.example.blog.service.PostService;
import com.example.blog.service.dto.PostDTO;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("/api/posts")
@AllArgsConstructor
public class PostResource {
    private  final PostService postService;

    @PostMapping
    public ResponseEntity<PostDTO> create(@Valid @RequestBody PostDTO postDTO) throws URISyntaxException {
        PostDTO result = postService.create(postDTO);
        return ResponseEntity
                .created(new URI("/api/posts/" + result.getId()))
                .body(result);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PostDTO> update(@PathVariable(value = "id") final Long id, @Valid @RequestBody PostDTO postDTO)
    {
        PostDTO result = postService.update(postDTO,id);
        return ResponseEntity
                .ok()
                .body(result);
    }

    @GetMapping
    public ResponseEntity<Page<PostDTO>> getAll(@PageableDefault(size = 6) Pageable pageable, PostFilter postFilter){

            postFilter.setPageable(pageable);

            Page<PostDTO> allPosts = postService.getAllFilteredWith(postFilter);
            return ResponseEntity.ok(allPosts);


    }
    @GetMapping("/{id}")
    public ResponseEntity<PostDTO> getById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(postService.getById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        postService.delete(id);
        return ResponseEntity
                .noContent()
                .build();
    }

    @GetMapping("/latestPosts")
    public ResponseEntity<List<PostDTO>> getLatestPosts(){
        List<PostDTO> latestPosts = postService.getLatestPosts();
        return ResponseEntity.ok(latestPosts);
    }
}
