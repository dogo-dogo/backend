package com.example.tooktook.model.dto.questionDto;

import lombok.Getter;

import java.util.List;

@Getter
public class QuestionDto {
    private Long qid;
    private String questions;
    private List<Long> answerIds;
    private List<String> giftImg;
    private List<String> mainText;
    private List<String> optionalText;
}
