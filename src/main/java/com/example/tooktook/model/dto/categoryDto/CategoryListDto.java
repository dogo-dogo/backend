package com.example.tooktook.model.dto.categoryDto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryListDto {
    private Long cid;
    private String categoryName;
    private Integer answerCount;
    private Integer totalCount;

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }
}
