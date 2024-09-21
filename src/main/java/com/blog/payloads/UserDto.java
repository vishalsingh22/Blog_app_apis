package com.blog.payloads;


import com.blog.entities.Comment;
import com.blog.entities.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Singular;

import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
public class UserDto {

private int id;

@NotEmpty
@Size(min=4,message = "Username must be of min 4 character !! ")
private String name;

@Email(message = "Email Address should me in valid format")
private String email;

@NotEmpty
private String password;

@NotEmpty
private String about;

    private Set<CommentDto> comments=new HashSet<>();
    private Set<RoleDto> roles=new HashSet<>();

}
