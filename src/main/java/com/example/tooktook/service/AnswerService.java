package com.example.tooktook.service;

import com.example.tooktook.common.response.ResponseCode;
import com.example.tooktook.exception.GlobalException;
import com.example.tooktook.model.dto.answerDto.AnswerDAO;
import com.example.tooktook.model.dto.answerDto.AnswerPageDto;
import com.example.tooktook.model.dto.answerDto.AnswerPageListDto;
import com.example.tooktook.model.dto.memberDto.MemberDetailsDto;
import com.example.tooktook.model.entity.Member;
import com.example.tooktook.model.entity.Notification;
import com.example.tooktook.model.repository.AnswerNeo4jRepository;
import com.example.tooktook.model.repository.MemberNeo4jRepository;
import com.example.tooktook.model.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    private final NotificationRepository notificationRepository;
    public AnswerDAO getAnswerDetails;

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

    /**
     *
     * @param memberId
     * After 와 Befroe 는 Notfication Node {id , beforeCnt, afterCnt}
     * if ( Before == After ) 만약에 그 이전 Answer의 수와 그 후에 조회한 데이터 수가 같으면
     * return " 받은 선물 조회 ~"
     * else (Before != After) 만약에 그 이전 수가 현재 조회한 수가 다르면 Answer의 데이터가 생긴 경우
     * After = Answer의 수를 카운트 (먼저 카운트 해야지 Before 의 수와 비교 할 수 있음)
     * @return After - Before

     */
    @Transactional
    public Integer getNotification(Long memberId){

        Notification notification = notificationRepository.findByNotification(memberId);
        Integer afterCnt = answerNeo4jRepository.countByMemberId(memberId);
        if(notification.getBeforeCnt() == afterCnt){
            return 0;
        }else{
//            notification.setBeforeCnt(afterCnt);
            Integer BeforeCnt = notification.getBeforeCnt();
            notification.setBeforeCnt(afterCnt);
            notificationRepository.save(notification);
            return afterCnt - BeforeCnt;
        }
    }
    public AnswerDAO getGetAnswerDetails(MemberDetailsDto member,Long answerId){

        answerNeo4jRepository.findByAnswerId(answerId)
                .orElseThrow(()->new GlobalException(ResponseCode.ErrorCode.NOT_FIND_ANSWER_ID));
        return answerNeo4jRepository.findByAnswersDetails(member.getId(),answerId);
    }
}