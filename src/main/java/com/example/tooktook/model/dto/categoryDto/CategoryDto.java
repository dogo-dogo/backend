package com.example.tooktook.model.dto.categoryDto;

import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
public class CategoryDto {
    private Long categoryId;

    private String categoryName;

}
