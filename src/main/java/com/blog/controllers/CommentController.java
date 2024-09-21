package com.blog.controllers;

import com.blog.payloads.ApiResponse;
import com.blog.payloads.CommentDto;
import com.blog.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/comments")

public class CommentController {
@Autowired
    private CommentService commentService;
    @PostMapping("/user/{userId}/post/{postId}/comments")
    public ResponseEntity<CommentDto> createComment(@RequestBody CommentDto comment,
          @PathVariable Integer postId,  @PathVariable Integer userId){
       CommentDto commentDto= this.commentService.createComment(comment,postId,userId);
       return new ResponseEntity<CommentDto>(commentDto, HttpStatus.CREATED);
    }
    @PostMapping("/{commentId}")
    public ResponseEntity<ApiResponse> deleteComment(@PathVariable Integer commentId){

        this.commentService.deleteComment(commentId);
        return new ResponseEntity<ApiResponse>(new ApiResponse("comment deleted Successfully",true),HttpStatus.OK);
    }
}
