package com.example.tooktook.service;

import com.example.tooktook.common.response.ResponseCode;
import com.example.tooktook.exception.GlobalException;
import com.example.tooktook.model.dto.answerDto.AnswerDto;
import com.example.tooktook.model.dto.answerDto.RandomAnswerDto;
import com.example.tooktook.model.dto.categoryDto.CategoryDto;
import com.example.tooktook.model.dto.categoryDto.CategoryListDto;
import com.example.tooktook.model.dto.questionDto.QuestionAllDto;
import com.example.tooktook.model.dto.questionDto.QuestionDto;
import com.example.tooktook.model.dto.enumDto.*;
import com.example.tooktook.model.dto.questionDto.QuestionRndDto;
import com.example.tooktook.model.entity.*;
import com.example.tooktook.model.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class Neo4jService {
    private final MemberNeo4jRepository memberNeo4jRepository;
    private final NotificationRepository notificationRepository;

    private final QuestionNeo4jRepository questionNeo4jRepository;

    private final CategoryNeo4jRepository categoryNeo4jRepository;
    private final AnswerNeo4jRepository answerNeo4jRepository;

    @Transactional
    public Member createMemberWithDefault(String memberEmail) {

        Member member = memberNeo4jRepository.findByLoginEmail(memberEmail)
                .orElseThrow( () -> new GlobalException(ResponseCode.ErrorCode.NOT_FIND_MEMBER));

        String memberNickName = member.getNickname();
        if (!member.getVisit()) {
            Notification notification = new Notification();
            notification.setBeforeCnt(0);
            member.addNotification(notification);
            for (CategoryEnum categoryEnum : CategoryEnum.values()) {
                Category category = new Category(categoryEnum.getText());
                member.addCategory(category);

                switch (categoryEnum.getText()){
                    case "Bye 2023":
                        Arrays.stream(Bye2023.values())
                                .map(bye2023Enum -> new Question(String.format(bye2023Enum.getText(), memberNickName)))
                                .forEach(category::askQuestion);
                        break;
                    case "칭찬":
                        Arrays.stream(Compliment.values())
                                .map(complimentEnum -> new Question(String.format(complimentEnum.getText(), memberNickName)))
                                .forEach(category::askQuestion);
                        break;
                    case "만약에":
                        Arrays.stream(Fi.values())
                                .map(fiEnum -> new Question(String.format(fiEnum.getText(),memberNickName,memberNickName)))
                                .forEach(category::askQuestion);
                        break;
                    case "성장":
                        Arrays.stream(Growth.values())
                                .map(growthEnum -> new Question(String.format(growthEnum.getText(),memberNickName)))
                                .forEach(category::askQuestion);
                        break;
                    case "Hello 2024":
                        Arrays.stream(Hello2024.values())
                                .map(hello2024Enum -> new Question(String.format(hello2024Enum.getText(),memberNickName)))
                                .forEach(category::askQuestion);
                        break;
                }

            }
            member.changeVisit();
            memberNeo4jRepository.save(member);

        }
        log.info("------------QuestionController Service 종료 ----------------");
        log.info("---------return Data > memberData.getMemberID : {} ",member.getMemberId());
        return member;

    }

    @Transactional
    public Long addAnswerToQuestion(Long questionId, AnswerDto answerDto) {

        // 만약에 Bye2023 에 7글자 제한 질답이면 제한을 둔다.
        Optional<Question> questionOptional = questionNeo4jRepository.findById(questionId);

        if (questionOptional.isPresent()) {
            Question question = questionOptional.get();
            Answer answer = new Answer();
            answer.setMainText(answerDto.getMainText());
            answer.setOptionalText(answerDto.getOptionalText());
            answer.setCreatedAt(LocalDateTime.now());

            // 질문과 답변을 연결
            question.askAnswer(answer);

            questionNeo4jRepository.save(question);
            answerNeo4jRepository.save(answer);

            return answer.getAnswerId();

//            return "답변이 추가되었습니다.";
        } else {
            log.error("답변 추가 에러 path : addAnswerToQuestion()");
            throw new GlobalException(ResponseCode.ErrorCode.NOT_FIND_QUESTION_ID);
        }
    }

    public List<CategoryListDto> getAllCategoryCount(String loginMember) {

        log.info("------------QuestionService 시작 ----------------");
        Long memberId = memberNeo4jRepository.findByLoginEmail(loginMember)
                .orElseThrow(() -> new GlobalException(ResponseCode.ErrorCode.NOT_FIND_MEMBER))
                .getMemberId();


        List<CategoryListDto> categoryListDtoList = categoryNeo4jRepository.findCategoryByCount(memberId);


        int totalSize = categoryListDtoList.stream()
                .mapToInt(CategoryListDto::getAnswerCount)
                .sum();

        categoryListDtoList.forEach(dto -> dto.setTotalCount(totalSize));

        log.info("------------QuestionController 종료 ----------------");

        return categoryListDtoList;
    }



    public List<QuestionDto> getCategoryToQuestion(String loginMember, Long cid) {
        log.info("------------QuestionService 시작 ----------------");
        Long memberId = memberNeo4jRepository.findByLoginEmail(loginMember)
                .orElseThrow(() -> new GlobalException(ResponseCode.ErrorCode.NOT_FIND_MEMBER))
                .getMemberId();

        log.info("------------QuestionService 종료 ----------------");
        return questionNeo4jRepository.findCategoryIdToQuestion(memberId,cid);

    }
    public List<QuestionAllDto> getAllCategoryToQuestions(String loginMember ){
        log.info("------------QuestionService 시작 ----------------");
        Long memberId = memberNeo4jRepository.findByLoginEmail(loginMember)
                .orElseThrow(() -> new GlobalException(ResponseCode.ErrorCode.NOT_FIND_MEMBER))
                .getMemberId();

        log.info("------------QuestionService 종료 ----------------");
        return questionNeo4jRepository.findByAllAnswers(memberId);
    }

    @Transactional
    public void deleteToAnswerId(String loginMember, Long answerId) {
        Long memberId = memberNeo4jRepository.findByLoginEmail(loginMember)
                .orElseThrow(() -> new GlobalException(ResponseCode.ErrorCode.NOT_FIND_MEMBER))
                .getMemberId();

        Answer answer = answerNeo4jRepository.findById(answerId)
                .orElseThrow(()->new GlobalException(ResponseCode.ErrorCode.NOT_FIND_ANSWER_ID));

        Notification notification = notificationRepository.findByNotification(memberId);
        notification.setBeforeCnt(notification.getBeforeCnt()-1);

        notificationRepository.save(notification);
        answerNeo4jRepository.delete(answer);
    }


    public RandomAnswerDto randomReadCategoryAndQuestion(Long memberId) {
        List<CategoryDto> categoryDtoList = new ArrayList<>();
        List<QuestionRndDto> questionDtoList = new ArrayList<>();
        Random rnd = new Random();
        int currIdxCid = 0;
        int currIdxQid = 0;
        log.info("--------- random service Start ------------");
        categoryDtoList = questionNeo4jRepository.findQuestionsByMemberId(memberId);
        // [1,2,3,4,5]

        log.info("-------------categoryDtoList size :  {} ------- ",categoryDtoList.size());

        int rndCid = Math.toIntExact(
                categoryDtoList.get(
                        rnd.nextInt(categoryDtoList.size())
                ).getCategoryId());

        for(int i =0; i<categoryDtoList.size(); i++){
            if(categoryDtoList.get(i).getCategoryId() == rndCid){
                currIdxCid = i;
                break;
            }
        }


        //random cid : [3]
        log.info("--------------categoryDtoList random idx : {} " , rndCid);
        log.info("--------------categoryDtoList random currIdxCid : {} " , currIdxCid);
        questionDtoList = questionNeo4jRepository.findCategoryIdToRandomQuestion(memberId, Long.valueOf(rndCid));
        // 카테고리가 랜덤 3번인 질문들을 조회  [5,6,7,8,9]

        int rndQid = Math.toIntExact(
                questionDtoList.get(
                        rnd.nextInt(questionDtoList.size())
                ).getQid());


        for(int i =0; i<questionDtoList.size(); i++){
            if(questionDtoList.get(i).getQid() == rndQid){
                currIdxQid = i;
                break;
            }
        }
        // random qid : [8]


        log.info("--------------categoryDtoList random currIdxCid : {} " , currIdxCid);
        log.info("--------------questionDtoList random currIdx : {} " , currIdxQid);
        log.info("-------------categoryDtoList size :  {} ------- ",categoryDtoList.get(currIdxCid).getCategoryName());
        log.info("-------------questionDtoList qid :  {} ------- ",questionDtoList.get(currIdxQid).getQid());
        log.info("-------------questionDtoList ques :  {} ------- ",questionDtoList.get(currIdxQid).getQuestions());

        return RandomAnswerDto.builder()
                .rndId(rndCid)
                .categoryText(categoryDtoList.get(currIdxCid).getCategoryName())
                .qid(questionDtoList.get(currIdxQid).getQid())
                .questionText(questionDtoList.get(currIdxQid).getQuestions())
                .build();

    }
}
