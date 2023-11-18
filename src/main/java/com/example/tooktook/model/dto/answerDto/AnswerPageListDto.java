package com.example.tooktook.model.dto.answerDto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class AnswerPageListDto {
        private Long id;
        private String category;
        private LocalDateTime createdAt;
        private String giftImg;
}
