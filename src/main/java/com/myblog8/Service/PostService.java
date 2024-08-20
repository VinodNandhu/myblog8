package com.myblog8.Service;

import com.myblog8.Payload.PostDto;
import com.myblog8.Payload.PostResponse;

import java.util.List;

public interface PostService {

    PostDto savePost(PostDto postDto);

    void deletePost(long id);

    PostDto updatePost(long id, PostDto postDto);

    PostDto getPostById(long id);


    PostResponse getPosts(int pageNo, int pageSize, String sortBy, String sortDir);
}

