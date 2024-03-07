package com.example.blog.api.resource;

import com.example.blog.service.CommentService;
import com.example.blog.service.dto.comment.CommentDTO;
import com.example.blog.service.dto.comment.CommentResponseDTO;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

@RestController
@RequestMapping("/api/comments")
@AllArgsConstructor
public class CommentResource {
    private final CommentService commentService;

    @PostMapping("")
    public ResponseEntity<CommentDTO> create(@Valid @RequestBody CommentDTO commentDTO) throws URISyntaxException {
        CommentDTO result = commentService.creat(commentDTO);
        return ResponseEntity
                .created(new URI("/api/comments/" + result.getId()))
                .body(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommentResponseDTO> get(@PathVariable("id") Long id) {
        CommentResponseDTO commentResponseDTO = commentService.get(id);
        return ResponseEntity.ok(commentResponseDTO);
    }

    @GetMapping("")
    public ResponseEntity<Page<CommentResponseDTO>> getCommentsByPost(@RequestParam(defaultValue = "0") int pageNumber,
                                                                @RequestParam(defaultValue = "5") int pageSize,
                                                                @RequestParam Long postId){
        Page<CommentResponseDTO> comments = commentService.getCommentsByPost(postId, pageNumber, pageSize);

        return ResponseEntity.ok(comments);
    }
}
