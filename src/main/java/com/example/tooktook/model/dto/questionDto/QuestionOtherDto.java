package com.example.tooktook.model.dto.questionDto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class QuestionOtherDto {

    private List<QuestionRndDto> others;
    private String CategoryName;
}
