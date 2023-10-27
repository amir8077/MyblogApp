package com.myblog1.repository;

import com.myblog1.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    List< Comment> findByPostId(long id);
    //List< Comment> findByPostIdOrEmail(long id,String email);
    //List< Comment> findByPostIdAndEmail(long id,String email);
   // boolean exitsByEmail(String email);
    //List< Comment> findByName(String name);
}

