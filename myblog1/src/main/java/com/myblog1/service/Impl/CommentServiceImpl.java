package com.myblog1.service.Impl;

import com.myblog1.entity.Comment;
import com.myblog1.entity.Post;
import com.myblog1.exception.ResourceNotFoundException;
import com.myblog1.payload.CommentDto;
import com.myblog1.repository.CommentRepository;
import com.myblog1.repository.PostRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService{

    private CommentRepository commentRepo;
    private PostRepository postRepo;
    private ModelMapper mapper;

    public CommentServiceImpl(CommentRepository commentRepo,PostRepository postRepo, ModelMapper mapper) {
        this.commentRepo = commentRepo;
        this.postRepo = postRepo;
        this.mapper = mapper;
    }

    @Override
    public CommentDto createComment(long postId, CommentDto commentDto) {
        Comment comment = mapToEntity(commentDto);
        Post post = postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));
        comment.setPost(post);

        Comment saved = commentRepo.save(comment);

        return mapToDto(saved) ;
    }


    @Override
    public List<CommentDto> getCommentsPostId(long postId) {
        Post post = postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post","id",postId));
        List<Comment> comments = commentRepo.findByPostId(postId);
        return comments.stream().map(comment ->mapToDto(comment)).collect(Collectors.toList());


    }

    @Override
    public void deleteCommentById(long postId, long id) {
        Post post = postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post","id",postId));
        commentRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("Post", "id",postId));
        commentRepo.deleteById(id);
    }

    @Override
    public CommentDto getCommentById(long postId, long id) {
        Post post = postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post","id",postId));
        Comment comment = commentRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));
        CommentDto commentDto = mapToDto(comment);
        return commentDto ;


    }

    @Override
    public CommentDto updateComment(Long postId, long commentId, CommentDto commentDto) {
        Post post = postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post","id",postId));
        Comment comment = commentRepo.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("Post", "id", commentId));
        comment.setName(commentDto.getName());
        comment.setEmail(commentDto.getEmail());
        comment.setBody(commentDto.getBody());
        Comment updateComment = commentRepo.save(comment);
        return mapToDto(updateComment);
    }



    private CommentDto mapToDto(Comment comment) {
        CommentDto commentDto = mapper.map(comment,CommentDto.class);
        return commentDto;
    }

    private Comment mapToEntity(CommentDto commentDto) {
        Comment comment = mapper.map(commentDto,Comment.class);
        return comment;
    }
}
