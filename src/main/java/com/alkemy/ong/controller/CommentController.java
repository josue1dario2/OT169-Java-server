package com.alkemy.ong.controller;

import com.alkemy.ong.dto.CommentRequestDto;
import com.alkemy.ong.dto.CommentResponseDto;
import com.alkemy.ong.service.impl.CommentServiceImpl;
import com.sendgrid.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/comments")
public class CommentController {

    private final CommentServiceImpl service;
    @Autowired
    public CommentController(CommentServiceImpl service){
        this.service = service;
    }

    @GetMapping
    public List<CommentResponseDto> getAllComments() {
        return service.getAllComments();
    }

    @PostMapping()
    public ResponseEntity<Void> addComment(@Valid @RequestBody CommentRequestDto commentRequestDto){
        return service.addComment(commentRequestDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> putComment(@PathVariable String id, @RequestBody @Valid CommentRequestDto commentRequestDto){
        return service.putComment(id,commentRequestDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void>delete(@PathVariable String id){
        return service.delete(id);
    }

    @GetMapping("/posts/{id}/comments")
    public ResponseEntity<?> getAllCommentsNews(@PathVariable String id) {
        try {
            return ResponseEntity.status(OK).body(service.getAllCommentsNews(id));
        } catch (Exception e) {
            return ResponseEntity.status(NOT_FOUND).body(e.getMessage());
        }
    }
}
