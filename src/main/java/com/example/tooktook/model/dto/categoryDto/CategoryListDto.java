package com.example.tooktook.model.dto.categoryDto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
public class CategoryListDto {
    private Long cid;
    private String categoryName;
    private Integer answerCount;

}
