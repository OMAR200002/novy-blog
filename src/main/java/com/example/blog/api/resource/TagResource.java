package com.example.blog.api.resource;

import com.example.blog.dao.repository.TagRepository;
import com.example.blog.domain.Tag;
import com.example.blog.service.TagService;
import com.example.blog.service.dto.PostDTO;
import com.example.blog.service.dto.TagDTO;
import com.example.blog.service.mapper.TagMapper;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/api/tags")
public class TagResource {
  private final TagService tagService;

    public TagResource(TagService tagService) {
        this.tagService = tagService;
    }

    @PostMapping("")
    public ResponseEntity<TagDTO> createTag(@Valid @RequestBody TagDTO tagDTO) throws URISyntaxException {

        TagDTO result = tagService.creatTag(tagDTO);
        return ResponseEntity
                .created(new URI("/api/tags/" + result.getId()))
                .body(result);
    }
    @PutMapping("/{id}")
    public ResponseEntity<TagDTO> updateTag(@PathVariable(value = "id") final Long id, @Valid @RequestBody TagDTO tagDTO)
            throws URISyntaxException {

        TagDTO result = tagService.save(tagDTO,id);
        return ResponseEntity
                .ok()
                .body(result);
    }

    @GetMapping("")
    public ResponseEntity<Page<TagDTO>> getAllTags(@RequestParam(defaultValue = "0") int pageNumber,
                                                     @RequestParam(defaultValue = "10") int pageSize){
        Page<TagDTO> allTags = tagService.getAllTags(pageNumber, pageSize);
        return ResponseEntity.ok(allTags);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TagDTO> getTag(@PathVariable("id") Long id) {
        TagDTO tagDTO = tagService.getTag(id);
        return ResponseEntity.ok(tagDTO);
    }


}
