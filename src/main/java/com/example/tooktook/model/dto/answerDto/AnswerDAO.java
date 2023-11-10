package com.example.tooktook.model.dto.answerDto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class AnswerDAO {

    private Long answerId;
    private String mainText;
    private String optionalText;
    private String giftImg;
}
