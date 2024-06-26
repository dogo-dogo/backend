package com.example.tooktook.service;

import com.example.tooktook.common.response.ResponseCode;
import com.example.tooktook.exception.GlobalException;
import com.example.tooktook.model.dto.answerDto.AnswerDAO;
import com.example.tooktook.model.dto.answerDto.AnswerPageDto;
import com.example.tooktook.model.dto.answerDto.AnswerPageListDto;
import com.example.tooktook.model.dto.categoryDto.CategoryNotify;
import com.example.tooktook.model.dto.memberDto.MemberDetailsDto;
import com.example.tooktook.model.entity.Notification;
import com.example.tooktook.model.repository.AnswerNeo4jRepository;
import com.example.tooktook.model.repository.NotificationRepository;
import com.example.tooktook.model.repository.QuestionNeo4jRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AnswerService {

    private final AnswerNeo4jRepository answerNeo4jRepository;
    private final NotificationRepository notificationRepository;

    private final QuestionNeo4jRepository questionNeo4jRepository;
    public AnswerPageDto getAnswersByCategory(Pageable pageable, Long memberId) {
        int curPage = pageable.getPageNumber()-1;
        int pageSize = pageable.getPageSize();

        List<AnswerPageListDto> answers = answerNeo4jRepository.findAllByCategoryFromAnswer(memberId);

        int startIdx = curPage * pageSize;
        int endIdx = Math.min(startIdx + pageSize, answers.size());


        if (startIdx > endIdx){
            log.error("------------ERROR  종료 ----------------");
            log.info("------------answerController > Service 종료 ----------------");
            throw new GlobalException(ResponseCode.ErrorCode.PAGE_OVER);
        }else{

            log.info("------------answerController > Service 종료 ----------------");
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

            log.info("------------answerController > Service 종료 ----------------");
            return afterCnt - BeforeCnt;
        }
    }

    public AnswerDAO getAnswerDetails(Long memberId, Long answerId) {

        answerNeo4jRepository.findByAnswerId(answerId)
                .orElseThrow(()->new GlobalException(ResponseCode.ErrorCode.NOT_FIND_ANSWER_ID));

        log.info("------------answerController > Service 종료 ----------------");
        return answerNeo4jRepository.findByAnswersDetails(memberId,answerId);
    }

    public List<Boolean> getAllcheckAnswerGreen(Long memberId) {
        Notification notification = notificationRepository.findByNotification(memberId);
        List<CategoryNotify> categoryNotify = questionNeo4jRepository.findAllByCounting(memberId);

        // 1,0,1,1,0 현재
        int[] totalAnswerCounts = categoryNotify.stream()
                .mapToInt(CategoryNotify::getAnswerCount)
                .toArray();

        // 0,0,1,1,0 과거
        int [] notificationGet = notification.getAnswerCounts();

        // 현재(totalAnswerCounts)  과거(notificationGet)
        //1,0,1,1,0                 0,0,2,1,0
        //T,F,F,F,F
        //(1+1)-1
        //(2+1)-1 // 현재값 + 1 - 1
        // 2,0,1,1,0                2,0,2,1,0
        // 4,1,2,3,1                3,1,3,4,0
        // 8,3,2,4,1                4,3,2,4,1
        // (8+1)-1                  8,3,2,4,1
        List<Boolean> result = new ArrayList<>();

        for (int i = 0; i <totalAnswerCounts.length ; i++) {
            int diff =totalAnswerCounts[i] - notificationGet[i];
            if(diff >= 1) {
                result.add(true);
            }else{
                result.add(false);
            }
        }
        return result;
    }
}