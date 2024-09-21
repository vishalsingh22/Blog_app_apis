package com.blog.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "posts")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int postId;
    @Column(name = "post_title" ,length=100,nullable = false)
    private String title;
    @Column(name = "post_content",length = 1000)
    private String content;
    @Column(name = "post_imageName" )
    private String imageName;
    @Column(name = "post_addedDate" )
    private Date addedDate;

    @ManyToOne
    @JoinColumn(name = "categoryid")
    private Category category;
    @ManyToOne
    private User user;

    @OneToMany(mappedBy = "post",cascade = CascadeType.ALL)
    private Set<Comment> comments=new HashSet<>();
}
