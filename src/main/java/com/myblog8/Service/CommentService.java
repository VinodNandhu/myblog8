package com.myblog8.Service;

import com.myblog8.Payload.CommentDto;

import java.util.List;

public interface CommentService {
    // TODO: Implement comment related functionality like save, delete, update, get all comments for a post

    CommentDto createComment(long postId, CommentDto commentDto );//2 things will go ,post id, comment dto

    CommentDto updateComment(long postId, long commentId, CommentDto commentDto);// 3 things

    CommentDto getCommentById(long postId, long commentId);

    List<CommentDto> getAllCommentsByPostId();

    void deleteCommentById(long postId, long commentId);
}
