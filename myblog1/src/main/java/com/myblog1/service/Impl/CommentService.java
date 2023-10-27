package com.myblog1.service.Impl;

import com.myblog1.entity.Comment;
import com.myblog1.payload.CommentDto;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.metamodel.SingularAttribute;
import java.io.Serializable;
import java.util.List;

public interface CommentService {
    CommentDto createComment(long postId, CommentDto commentDto);
    List<CommentDto> getCommentsPostId(long postId);


    void deleteCommentById(long postId, long id);

    CommentDto getCommentById(long postId, long id);
    CommentDto updateComment(Long postId, long commentId, CommentDto commentDto);
}
