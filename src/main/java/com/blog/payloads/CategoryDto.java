package com.blog.payloads;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class CategoryDto {
    private String categoryId;
    @NotEmpty
    @Size(min=3,message = "title must be greater than 3 character")
    private String categoryTitle;
    @NotEmpty
    @Size(min = 10)
    private String categoryDescription;

}
