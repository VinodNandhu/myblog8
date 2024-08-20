package com.myblog8.Controller;

import com.myblog8.Payload.CommentDto;
import com.myblog8.Service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class CommentController {
   private CommentService commentService;// use this object to save


    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

//http://localhost:8080/api/posts/1/comments
    @PostMapping("/{postId}/comments")
    public ResponseEntity<CommentDto> createComment(@PathVariable(value = "postId")long postId,
                                                    @RequestBody CommentDto commentDto){

        return new ResponseEntity<>( commentService.createComment(postId, commentDto),HttpStatus.CREATED);// 201

    }
    //http://localhost:8080/api/5
    @DeleteMapping("/{postId}/comments/{commentId}")
    public ResponseEntity<String> deleteCommentById ( @PathVariable(value = "postId") long postId,
    @PathVariable(value = "commentId") long commentId) {
        commentService.deleteCommentById(postId, commentId);
        return new ResponseEntity<>("Comment is deleted", HttpStatus.OK);
    }
    //   http://localhost:8080/api/posts/1/comments/{Id}
    @PutMapping("/{postId}/comments/{Id}")
    public ResponseEntity<CommentDto> updateComment(@PathVariable (value = "postId") long postId,
              @PathVariable(value = "Id") long commentId,     @RequestBody CommentDto commentDto){
       CommentDto updatedComment = commentService.updateComment(postId, commentId,commentDto);
        return new ResponseEntity<>( updatedComment , HttpStatus.OK);
    }

    // GET endpoint to retrieve a comment by its Id one post and its comment
    @GetMapping("/{postId}/comments/{id}")
    public ResponseEntity<CommentDto> getCommentById(
            @PathVariable(value = "postId") long postId,
            @PathVariable(value = "commentId") long commentId) {

        // Retrieve the comment using postId and commentId
        CommentDto commentDto = commentService.getCommentById(postId, commentId);

        // Return response with retrieved comment and HTTP status code 200
        return new ResponseEntity<>(commentDto, HttpStatus.OK);
    }
    // GET endpoint to retrieve all comments for a post
    @GetMapping("/comments")
    public ResponseEntity<List<CommentDto>> getAllCommentsByPostId (){

        // Retrieve all comments for the given postId
        List<CommentDto> commentDtoList = commentService.getAllCommentsByPostId();

        // Return response with retrieved comments and HTTP status code 200
        return new ResponseEntity<>(commentDtoList, HttpStatus.OK);
    }
    }

