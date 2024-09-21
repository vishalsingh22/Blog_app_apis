package com.blog.services.impl;

import com.blog.entities.Comment;
import com.blog.entities.Post;
import com.blog.entities.User;
import com.blog.exceptions.ResourceNotFoundException;
import com.blog.payloads.CommentDto;
import com.blog.repositories.CommentRepo;
import com.blog.repositories.PostRepo;
import com.blog.repositories.UserRepo;
import com.blog.services.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private PostRepo postRepo;
    @Autowired
    private CommentRepo commentRepo;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private ModelMapper modelMapper;
    @Override
    public CommentDto createComment(CommentDto commentDto, Integer postId,Integer userId) {

        Post post=this.postRepo.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post","Post Id",postId));
        User user=this.userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User","user Id",userId));
        Comment comment= this.modelMapper.map(commentDto, Comment.class);
       comment.setPost(post);
       comment.setUser(user);
       Comment savedComment=  this.commentRepo.save(comment);

        return this.modelMapper.map(savedComment,CommentDto.class);
    }

    @Override
    public void deleteComment(Integer commentId) {
        Comment comment=this.commentRepo.findById(commentId).orElseThrow(()-> new ResourceNotFoundException("Comment","Comment Id",commentId));
       this.commentRepo.delete(comment);
    }
}
