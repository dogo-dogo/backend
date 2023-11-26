package com.example.tooktook.model.dto.answerDto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
