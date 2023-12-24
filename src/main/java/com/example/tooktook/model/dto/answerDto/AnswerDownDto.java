package com.example.tooktook.model.dto.answerDto;

import lombok.Getter;

import java.util.List;

@Getter
public class AnswerDownDto {
    private List<String> queText;
    private String categoryText;
    private String giftImg;
    private String mainText;
    private String optText;
}
