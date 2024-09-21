package com.blog.controllers;

import com.blog.config.AppConstants;
import com.blog.payloads.ApiResponse;
import com.blog.payloads.PostDto;
import com.blog.payloads.PostResponse;
import com.blog.services.FileService;
import com.blog.services.PostService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping("/api/posts")

public class PostController {
    @Autowired
    private PostService postService;
    @Autowired
    private FileService fileService;
    @Value("${project.image}")
    private String path;
    @PostMapping("/user/{userId}/category/{categoryId}/post")
    public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto,
                                              @PathVariable Integer userId,
                                              @PathVariable Integer categoryId)
    {
        PostDto createPost=this.postService.createPost(postDto,userId,categoryId);

        return new ResponseEntity<PostDto>(createPost, HttpStatus.CREATED);

    }

    @GetMapping("/user/{userId}/posts")
    public ResponseEntity<List<PostDto>> getPostByUser(@PathVariable Integer userId){

        List<PostDto> posts=this.postService.getPostsByUser(userId);
        return new ResponseEntity<List<PostDto>>(posts,HttpStatus.OK);

    }

    @GetMapping("/category/{categoryId}/posts")
    public ResponseEntity<List<PostDto>> getPostByCategory(@PathVariable Integer categoryId){

        List<PostDto> posts=this.postService.getPostsByCategory(categoryId);
        return new ResponseEntity<List<PostDto>>(posts,HttpStatus.OK);

    }
    @GetMapping("/")
    public ResponseEntity<PostResponse> getAllPost(
            @RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER,required = false) Integer pageNumber ,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE,required = false) Integer pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.SORT_DIR,required = false) String sortDir

    ){

        PostResponse postResponse=this.postService.getAllPost(pageNumber,pageSize,sortBy,sortDir);
        return new ResponseEntity<PostResponse>(postResponse,HttpStatus.OK);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<PostDto> getPostById(@PathVariable Integer postId){

        PostDto postDto=this.postService.getPostById(postId);
        return new ResponseEntity<PostDto> (postDto,HttpStatus.OK);
    }
@DeleteMapping("/{postId}")
    public ApiResponse delete(@PathVariable Integer postId)
    {
       this.postService.deletePost(postId);

       return new ApiResponse("post Deleted successfully",true);
    }

    @PutMapping("/{postId}")
    public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto,@PathVariable Integer postId)
    {
        PostDto updatePost=this.postService.updatePost(postDto,postId);
        return new ResponseEntity<PostDto>(updatePost,HttpStatus.OK);
    }

    @GetMapping("/search/{keyword}")
    public ResponseEntity<List<PostDto>> searchPostByTitle(@PathVariable String keyword){
        List<PostDto> postDtos= this.postService.searchPosts(keyword);
        return new ResponseEntity<List<PostDto>>(postDtos,HttpStatus.OK);

    }

    @PostMapping("/image/upload/{postId}")
    public ResponseEntity<PostDto> uploadImage(
            @RequestParam("image") MultipartFile multipartFile,
            @PathVariable Integer postId
            ) throws IOException {

        PostDto postDto = this.postService.getPostById(postId);
      String fileName=  this.fileService.uploadImage(path,multipartFile);

      postDto.setImageName(fileName);
      PostDto updatePost=this.postService.updatePost(postDto,postId);
      return new ResponseEntity<PostDto>(updatePost,HttpStatus.OK);

    }

    @GetMapping(value = "/image/{imageName}",produces = MediaType.IMAGE_JPEG_VALUE)
    public void downloadImage(
            @PathVariable String imageName, HttpServletResponse response
            ) throws IOException{
        InputStream resource=this.fileService.getResource(path,imageName);
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(resource,response.getOutputStream());
    }

}
