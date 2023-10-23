package com.example.tooktook.service;

import com.example.tooktook.common.response.ResponseCode;
import com.example.tooktook.exception.GlobalException;
import com.example.tooktook.model.dto.answerDto.AnswerPageDto;
import com.example.tooktook.model.dto.answerDto.AnswerPageListDto;
import com.example.tooktook.model.dto.memberDto.MemberDetailsDto;
import com.example.tooktook.model.entity.Answer;
import com.example.tooktook.model.repository.AnswerNeo4jRepository;
import com.example.tooktook.model.repository.MemberNeo4jRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AnswerService {

    private final AnswerNeo4jRepository answerNeo4jRepository;

    public AnswerPageDto getAnswersByCategory(Pageable pageable, MemberDetailsDto member) {
        int curPage = pageable.getPageNumber()-1;
        int pageSize = pageable.getPageSize();

        Long memberId = member.getId();

        List<AnswerPageListDto> answers = answerNeo4jRepository.findAllByCategoryFromAnswer(memberId);

        int startIdx = curPage * pageSize;
        int endIdx = Math.min(startIdx + pageSize, answers.size());


        if (startIdx > endIdx){
            throw new GlobalException(ResponseCode.ErrorCode.PAGE_OVER);
        }else{
            List<AnswerPageListDto> pagedAnswers = answers.subList(startIdx, endIdx);
            return new AnswerPageDto(pagedAnswers, curPage +1, calculateTotalPages(answers.size(), pageSize));
        }


    }
    private int calculateTotalPages(int totalResults, int pageSize) {
        return (int) Math.ceil((double) totalResults / pageSize);
    }
}