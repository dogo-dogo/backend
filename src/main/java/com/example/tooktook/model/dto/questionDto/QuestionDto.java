package com.example.tooktook.model.dto.questionDto;

import lombok.Getter;

import java.util.List;

@Getter
public class QuestionDto {
    private Long qid;
    private String questions;
    private List<Integer> aid;
}
