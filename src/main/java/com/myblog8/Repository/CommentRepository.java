package com.myblog8.Repository;

import com.myblog8.Entity.Comment;
import com.myblog8.Payload.CommentDto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByPostId(long postId);

}
