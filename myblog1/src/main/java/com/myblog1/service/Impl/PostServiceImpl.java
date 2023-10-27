package com.myblog1.service.Impl;

import com.myblog1.entity.Post;
import com.myblog1.exception.ResourceNotFoundException;
import com.myblog1.payload.PostDto;
import com.myblog1.repository.PostRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService{
    private PostRepository postRepo;
    private ModelMapper mapper;

    public PostServiceImpl(PostRepository postRepo,ModelMapper mapper ) {

        this.postRepo = postRepo;
        this.mapper= mapper;
    }


    @Override
    public PostDto createPost(PostDto postDto) {

       //covert to dto to entity
        Post post= mapToEntity(postDto);
        Post savedPost = postRepo.save(post);

        //convert entity to dto
        PostDto dto = mapToDto(savedPost);
        return dto;
    }

    @Override
    public List<PostDto> getAllPosts(int pageNo,int pageSize,String  sortBy,String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<Post> posts = postRepo.findAll(pageable);
        List<Post> content = posts.getContent();
        List<PostDto> postDtos  = content.stream().map(post -> mapToDto(post)).collect(Collectors.toList());
        return  postDtos;

    }


    @Override
    public PostDto getPostById(long id) {
        Post post = postRepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("Post","id",id));
        return mapToDto(post);

    }


    @Override
    public PostDto updatePost(PostDto postDto, long id) {
        Post post = postRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());


        Post updatePost = postRepo.save(post);
        return mapToDto(updatePost);
    }


    @Override
    public void deletePostById(long id) {
        Post post = postRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
        postRepo.delete(post);

    }

    PostDto mapToDto(Post post) {
        PostDto postDto = mapper.map(post, PostDto.class);
       /*PostDto postDto = new PostDto();
        postDto.setId(post.getId());
        postDto.setTitle(post.getTitle());
        postDto.setContent(post.getContent());
        postDto.setDescription(post.getDescription());*/
        return postDto;
    }
    Post mapToEntity(PostDto postDto){
        Post post = mapper.map(postDto, Post.class);
       /* Post post = new Post();
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        post.setDescription(postDto.getDescription());*/
        return post;
    }


}
