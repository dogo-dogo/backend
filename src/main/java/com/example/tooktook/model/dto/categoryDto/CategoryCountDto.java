package com.example.tooktook.model.dto.categoryDto;


import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class CategoryCountDto {
    private List<CategoryListDto> categoryLists;
    private totalCountDto totalCount;
}
