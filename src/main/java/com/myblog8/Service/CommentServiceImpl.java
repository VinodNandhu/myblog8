package com.myblog8.Service;

import com.myblog8.Entity.Comment;
import com.myblog8.Entity.Post;
import com.myblog8.Payload.CommentDto;
import com.myblog8.Repository.CommentRepository;
import com.myblog8.Repository.PostRepository;
import com.myblog8.exception.ResourceNotFound;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service

public class CommentServiceImpl  implements CommentService {

    private CommentRepository commentRepoository;// to save in data in repository. we use this object
    private PostRepository postRepository;
    private ModelMapper modelMapper; // covert dto to entity in one line with help of  modelmapper

    public CommentServiceImpl(CommentRepository commentRepoository, PostRepository postRepository, ModelMapper mapper) {
        this.commentRepoository = commentRepoository;
        this.postRepository = postRepository;
        this.modelMapper = mapper;
    }

    @Override
    public CommentDto createComment(long postId, CommentDto commentDto) {
      Comment comment = mapToEntity(commentDto);

      Post post = postRepository.findById(postId).orElseThrow(
              () -> new ResourceNotFound( "Post not found with id: " + postId));// if post is not

      comment.setPost(post);                                                      // found with id, throw

        Comment savedcomment = commentRepoository.save(comment);
        // we can't give the "entity" to the client so we convert" entity to Dto "
        CommentDto dto = mapToDto(savedcomment);
        return dto;
    }



    @Override
    public CommentDto updateComment(long postId, long commentId, CommentDto commentDto) {
        // retrieve post entity by id
     Post post  = postRepository.findById(postId).orElseThrow(
             () -> new ResourceNotFound( "Post id: " + postId));
     //retrieve comment by id
       Comment comment = commentRepoository.findById(commentId).orElseThrow(
               () -> new ResourceNotFound( "Comment id: " + commentId));
        if (!comment.getPost().getId().equals(post.getId())) {
            throw new IllegalArgumentException("Comment does not belong to the specified post");
        }

//    if is true
        comment.setName(commentDto.getName());
        comment.setEmail(commentDto.getEmail());
        comment.setBody(commentDto.getBody());

        Comment updatedComment = commentRepoository.save(comment);
        return mapToDto(updatedComment);

    }

    @Override
    public CommentDto getCommentById(long postId,long commentId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFound("Post not found with id: " + postId));

        Comment comment = commentRepoository.findById( commentId)
                .orElseThrow(() -> new ResourceNotFound("Comment not found with id: " + commentId));

        if (!comment.getPost().getId().equals(post.getId())) {
            throw new IllegalArgumentException("Comment does not belong to the specified post");
        }

        return mapToDto(comment);
    }

    @Override
    public List<CommentDto> getAllCommentsByPostId() {
        List<Comment> comments = commentRepoository.findAll();
        List<CommentDto> commentDtos = comments.stream().map(comment -> mapToDto(comment)).collect(Collectors.toList());
        return commentDtos ; // its go to the controller layer
    }

    @Override
    public void deleteCommentById(long postId, long commentId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFound("Post not found with id: " + postId));

        Comment comment = commentRepoository.findById( commentId)
                .orElseThrow(() -> new ResourceNotFound("Comment not found with id: " + commentId));
        commentRepoository.deleteById(commentId);
    
    }
    // method to convert entity to dto

    private CommentDto mapToDto(Comment savedcomment) {
        CommentDto CoverdedEntity = modelMapper.map(savedcomment, CommentDto.class);
        return (CoverdedEntity);
    }

    private Comment mapToEntity(CommentDto commentDto) {
        //  dto To entity
        Comment CoverdedDto = modelMapper.map(commentDto, Comment.class);
        return CoverdedDto;

    }
}
