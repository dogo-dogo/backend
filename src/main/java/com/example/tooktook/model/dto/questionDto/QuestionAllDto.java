package com.example.tooktook.model.dto.questionDto;

import com.example.tooktook.model.dto.answerDto.AnswerDAO;
import lombok.Getter;

import java.util.List;

@Getter
public class QuestionAllDto {

    private List<Long> qid;
    private List<String> questions;
    private Long cid;
    private String categoryName;

}

