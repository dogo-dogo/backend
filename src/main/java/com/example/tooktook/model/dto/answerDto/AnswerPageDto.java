package com.example.tooktook.model.dto.answerDto;

import com.example.tooktook.model.entity.Answer;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
public class AnswerPageDto {
   private List<AnswerPageListDto> answers;
   private int curPage;
   private int totalPage;

}
