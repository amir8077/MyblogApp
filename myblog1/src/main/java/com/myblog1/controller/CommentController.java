package com.myblog1.controller;

import com.myblog1.entity.Comment;
import com.myblog1.payload.CommentDto;
import com.myblog1.service.Impl.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@RestController
@RequestMapping("/api/")
public class CommentController {
    private CommentService commentService;


    public CommentController(CommentService commentService) {
        this.commentService = commentService;

    }
    @PostMapping("/posts/{postId}/comments")
    public ResponseEntity<CommentDto> createComment(@PathVariable(value = "postId")long postId, @RequestBody CommentDto commentDto){
        CommentDto commented = commentService.createComment(postId, commentDto);
        return new ResponseEntity<>(commented, HttpStatus.CREATED);
    }
    @GetMapping("/posts/{postId}/comments")
    public List<CommentDto> getCommentPostById(@PathVariable(value = "postId")long postId) {

        return commentService.getCommentsPostId(postId);

    }
    @DeleteMapping("/posts/{postId}/comments/{id}")
    public ResponseEntity<String> deleteCommentById(@PathVariable(value = "postId")long postId,@PathVariable(value = "id")long id) {

        commentService.deleteCommentById(postId,id);
        return new ResponseEntity<>("comment is deleted", HttpStatus.OK);

    }
    @GetMapping("/posts/{postId}/comments/{id}")
    public ResponseEntity<CommentDto> getCommentById(@PathVariable(value = "postId")long postId,@PathVariable(value = "id")long id) {

        CommentDto dto = commentService.getCommentById(postId, id);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }
    @PutMapping("/posts/{postId}/comments/{commentId}")
    public ResponseEntity<CommentDto> updateComment(@PathVariable(value="postId")Long postId,@PathVariable(value="commentId")long commentId,@RequestBody CommentDto commentDto){
        CommentDto dto = commentService.getCommentById(postId, commentId);
        return new ResponseEntity<>(dto,HttpStatus.OK);
    }
}












