package com.example.tooktook.model.dto.answerDto;

import com.example.tooktook.model.dto.categoryDto.CategoryDto;
import com.example.tooktook.model.dto.questionDto.QuestionDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RandomAnswerDto {
    private Integer rndId;
    private String categoryText;
    private Long qid;
    private String questionText;

}
