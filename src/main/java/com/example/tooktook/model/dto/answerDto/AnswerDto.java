package com.example.tooktook.model.dto.answerDto;

import lombok.Getter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@Getter
public class AnswerDto {

    @NotBlank(message = "질문에 답변을 입력 해주세요 ")
    @Length(min = 2, max = 20, message = "필수 답변은 최소 2글자 ~ 20글자 입니다.")
    private String mainText;
    @Length(min = 2, max = 50, message = "선택 답변은 최소 2글자 ~ 50글자 입니다.")
    private String optionalText;
}
