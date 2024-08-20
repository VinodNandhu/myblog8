package com.myblog8.Controller;

import com.myblog8.Payload.PostDto;
import com.myblog8.Payload.PostResponse;
import com.myblog8.Service.PostService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/post")
public class PostController {

    private PostService postservice; // object of service layer is created

    public PostController(PostService postservice) {
        this.postservice = postservice;
    }
     //     http://localhost:8080/api/post
    @PostMapping
    public ResponseEntity<?> savePost(@Valid @RequestBody PostDto postDto, BindingResult result){ // the content is copied from json
        if(result.hasErrors()){
            return new ResponseEntity<>(result.getFieldError().getDefaultMessage(),HttpStatus.INTERNAL_SERVER_ERROR); // 500
        }
        PostDto dto = postservice.savePost(postDto);//postDto is given to service layer
        return new ResponseEntity<>(dto, HttpStatus.CREATED);// 201
    }
    //http://localhost:8080/api/post/1
    @DeleteMapping("{id}")
    public ResponseEntity<String> deletePost(@PathVariable("id") long id){
        postservice.deletePost(id);
        return new ResponseEntity<>("post is deleted", HttpStatus.OK);// 200
    }
    //http://localhost:8080/api/post/1
    @PutMapping("{id}")
    public ResponseEntity<PostDto> updatePost(@PathVariable("id") long id, @RequestBody PostDto postDto){
        PostDto dto = postservice.updatePost(id, postDto);
        return  new ResponseEntity<>(dto,HttpStatus.OK); // 200
    }
    //http://localhost:8080/api/post/1
    @GetMapping("{id}")
    public ResponseEntity<PostDto> getPostById(@PathVariable("id") long id){
        PostDto dto = postservice.getPostById(id);
        return new ResponseEntity<>(dto, HttpStatus.OK); // 200

    }
   //   http://localhost:8080/api/post?pageNO=0&pageSize=5&sortBy=title&sortDir=desc
    @GetMapping
    public PostResponse getPosts(
        @RequestParam(value ="pageNo", defaultValue = "0", required = false) int pageNo,
        @RequestParam(value ="pageSize", defaultValue = "5", required = false) int pageSize,
        @RequestParam(value ="sortBy", defaultValue = "id", required = false) String sortBy,
        @RequestParam(value ="sortDir", defaultValue = "id", required = false) String sortDir


        ){
        PostResponse postResponse = postservice.getPosts(pageNo,pageSize, sortBy,sortDir);
       return postResponse;
    }
}


