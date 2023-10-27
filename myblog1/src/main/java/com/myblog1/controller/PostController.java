package com.myblog1.controller;

import com.myblog1.payload.PostDto;
import com.myblog1.service.Impl.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {
    private PostService postService;
    private ModelMapper mapper;

    public PostController(PostService postService,ModelMapper mapper) {

        this.postService = postService;
        this.mapper= mapper;
    }
@PreAuthorize("hasRole('ADMIN' )")
    @PostMapping
    public ResponseEntity<?> createPost(@Valid @RequestBody PostDto postDto ,BindingResult result) {
        if(result.hasErrors()){
            return new ResponseEntity<>(result.getFieldError().getDefaultMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }


        PostDto savedDto = postService.createPost(postDto);
        return new ResponseEntity<>(savedDto, HttpStatus.CREATED);

    }



//http://localhost:8080/api/posts?pageNo=0&pageSize=3&sortBy=title
    @GetMapping
    public List<PostDto> getAllPosts(
        @RequestParam(value = "pageNo",defaultValue = "0",required = false)int pageNo,
        @RequestParam(value = "pageSize",defaultValue = "10",required = false)int pageSize,
        @RequestParam(value= "sortBy",defaultValue = "id",required = false)String sortBy,
        @RequestParam(value= "sortDir",defaultValue = "asc",required = false)String sortDir){
        return postService.getAllPosts(pageNo,pageSize,sortBy,sortDir);

    }
    @GetMapping("/{id}")
    public ResponseEntity<PostDto> getPostById(@PathVariable(name="id") long id){
        return ResponseEntity.ok(postService.getPostById(id));

    }
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto, @PathVariable(name = "id") long id){
        return new ResponseEntity<>(postService.updatePost(postDto, id),HttpStatus.OK);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePostById(@PathVariable(name= "id") long id){
        postService.deletePostById(id);
        return new ResponseEntity<>("Post entity successfully.",HttpStatus.OK);
    }

}
