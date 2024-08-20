package com.myblog8.Service;

import com.myblog8.Entity.Post;
import com.myblog8.Payload.PostDto;
import com.myblog8.Payload.PostResponse;
import com.myblog8.Repository.PostRepository;
import com.myblog8.exception.ResourceNotFound;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.data.domain.Sort.by;


@Service
public class PostServiceImpl implements PostService {

    private PostRepository postRepository;// this will be post repository object

    private ModelMapper modelMapper; // covert dto to entity in one line with help of  modelmapper

    public PostServiceImpl(PostRepository postRepository, ModelMapper modelMapper) {
        this.postRepository = postRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public PostDto savePost(PostDto postDto) {
        Post post = mapToEntity(postDto); // dto to entity

        Post savedpost = postRepository.save(post);
        PostDto dto = mapToDto(savedpost);// entity to dto
      return  dto; // it goes to controller
    }

    @Override
    public void deletePost(long id) {
        postRepository.deleteById(id);

    }

    @Override
    public PostDto updatePost(long id, PostDto postDto) { // 2 things to do 1-is post update. 2-  is error
        Post post = postRepository.findById(id).orElseThrow(
                ()->new ResourceNotFound("Post not found with id " + id));
       // dto to entity
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        post.setDescription(postDto.getDescription());

        Post updatedpost = postRepository.save(post); //updated entity
        //entity  to dto
        PostDto dto = mapToDto(updatedpost);
        return dto; // it goes to controller
    }

    @Override // get 1 post
    public PostDto getPostById(long id) {
        Post post = postRepository.findById(id).orElseThrow(
                () -> new ResourceNotFound("Post not found with id " + id));
        PostDto dto = mapToDto(post);
        return dto;// its goes to controller layer
    }

    @Override
    public PostResponse getPosts(int pageNo, int pageSize, String sortBy, String sortDir) {
        Sort  sort= by(sortBy).ascending();
        Sort.by(sortBy).descending();

         Pageable pageable = PageRequest.of(pageNo,pageSize,sort);
        Page<Post> pagePosts = postRepository.findAll(pageable);


        List<Post> posts = pagePosts.getContent();
        List<PostDto> postDtos = posts.stream().map(post -> mapToDto(post)).collect(Collectors.toList());

        PostResponse postResponse = new PostResponse();
        postResponse.setPostDto(postDtos);
        postResponse.setPageNo(pagePosts.getNumber());
        postResponse.setPageSize(pagePosts.getSize());
        postResponse.setTotalElements(pagePosts.getTotalElements());
        postResponse.setLast(pagePosts.isLast());
        postResponse.setTotalPages(pagePosts.getTotalPages());
        return postResponse; // its goes to controller layer


    }


    PostDto mapToDto(Post post){
    // entity to dto
       PostDto dto = modelMapper.map(post, PostDto.class);
 //   PostDto dto = new PostDto();
//        dto.setId(post.getId());
//        dto.setTitle(post.getTitle());
//        dto.setDescription(post.getDescription());
//        dto.setContent(post.getContent());
        return  dto;
}
     Post mapToEntity(PostDto postDto){
       Post post = modelMapper.map(postDto,Post.class);
//         Post post = new Post(); // we can not save a dto in the database so
//         // converts Dto to entity
//         post.setTitle(postDto.getTitle());
//         post.setDescription(postDto.getDescription());
//         post.setContent(postDto.getContent());
         return  post;

     }


}
