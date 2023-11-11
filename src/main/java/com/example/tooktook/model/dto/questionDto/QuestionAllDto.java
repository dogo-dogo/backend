package com.example.tooktook.model.dto.questionDto;

import lombok.Getter;

import java.util.List;

@Getter
public class QuestionAllDto {

    private Long qid;
    private String questions;
    private List<Long> answerIds;
}

