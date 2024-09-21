package com.blog.services.impl;

import com.blog.entities.Category;
import com.blog.exceptions.ResourceNotFoundException;
import com.blog.payloads.CategoryDto;
import com.blog.repositories.CategoryRepo;
import com.blog.services.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepo categoryRepo;
    @Autowired
    private ModelMapper modelMapper;
    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {

      Category cat=  this.modelMapper.map(categoryDto, Category.class);
      Category addedCat = this.categoryRepo.save(cat);
        return this.modelMapper.map(addedCat,CategoryDto.class);
    }

    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId) {

        Category category=this.categoryRepo.findById(categoryId).
                orElseThrow(()-> new ResourceNotFoundException("Category","Category Id",categoryId));

        category.setCategoryTitle(categoryDto.getCategoryTitle());
        category.setCategoryDescription(categoryDto.getCategoryDescription());

        Category updatedCategory=this.categoryRepo.save(category);

        return this.modelMapper.map(updatedCategory,CategoryDto.class);
    }

    @Override
    public void deleteCategory(Integer categoryId) {

    Category category=this.categoryRepo.findById(categoryId).
                orElseThrow(()-> new ResourceNotFoundException("Category","Category Id",categoryId));

    this.categoryRepo.delete(category);


    }

    @Override
    public CategoryDto getCategory(Integer categoryId) {
        Category category=this.categoryRepo.findById(categoryId).
                orElseThrow(()-> new ResourceNotFoundException("Category","Category Id",categoryId));

        return this.modelMapper.map(category,CategoryDto.class);
    }

    @Override
    public List<CategoryDto> getCategories() {
        List<Category> categories=this.categoryRepo.findAll();
      List<CategoryDto> categoryDto = categories.stream().map((cat)->this.modelMapper.map(cat,CategoryDto.class)).collect(Collectors.toList());

        return categoryDto;
    }
}
