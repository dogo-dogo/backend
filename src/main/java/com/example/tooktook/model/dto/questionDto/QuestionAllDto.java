package com.example.tooktook.model.dto.questionDto;

import com.example.tooktook.model.dto.answerDto.AnswerDAO;
import lombok.Getter;

import java.util.List;

@Getter
public class QuestionAllDto {

    private Long qid;
    private String questions;
    private List<AnswerDAO> answers;
}

