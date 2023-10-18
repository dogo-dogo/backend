package com.example.tooktook.service;

import com.example.tooktook.exception.ErrorCode;
import com.example.tooktook.exception.GlobalException;
import com.example.tooktook.model.dto.AnswerDto;
import com.example.tooktook.model.dto.CategoryListDto;
import com.example.tooktook.model.dto.QuestionDto;
import com.example.tooktook.model.entity.Category;
import com.example.tooktook.model.entity.Answer;
import com.example.tooktook.model.entity.Member;
import com.example.tooktook.model.entity.Question;
import com.example.tooktook.model.enumDto.*;
import com.example.tooktook.model.repository.AnswerNeo4jRepository;
import com.example.tooktook.model.repository.CategoryNeo4jRepository;
import com.example.tooktook.model.repository.MemberNeo4jRepository;
import com.example.tooktook.model.repository.QuestionNeo4jRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
public class Neo4jService {
    private final MemberNeo4jRepository memberNeo4jRepository;

    private final QuestionNeo4jRepository questionNeo4jRepository;

    private final CategoryNeo4jRepository categoryNeo4jRepository;
    private final AnswerNeo4jRepository answerNeo4jRepository;

    @Transactional
    public Member createMemberWithDefault(Long memberId) {

        Member member = memberNeo4jRepository.findByMemberId(memberId)
                .orElseThrow( () -> new GlobalException(ErrorCode.NOT_FIND_MEMBER_ID));

        String memberNickName = member.getNickname();

        if (!member.getVisit()) {

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
        return member;

    }

    @Transactional
    public void addAnswerToQuestion(Long questionId, AnswerDto answerDto) {
        Optional<Question> questionOptional = questionNeo4jRepository.findById(questionId);

        if (questionOptional.isPresent()) {
            Question question = questionOptional.get();
            Answer answer = new Answer();
            answer.setMainText(answerDto.getMainText());
            answer.setOptionalText(answer.getOptionalText());

            // 질문과 답변을 연결
            question.askAnswer(answer);

            questionNeo4jRepository.save(question);
            answerNeo4jRepository.save(answer);

//            return "답변이 추가되었습니다.";
        } else {
            throw new GlobalException(ErrorCode.NOT_FIND_QUESTION_ID);
        }
    }

    public List<CategoryListDto> getAllCategoryCount(Long loginMember) {

        Long memberId = memberNeo4jRepository.findByMemberId(loginMember)
                .orElseThrow(() -> new GlobalException(ErrorCode.NOT_FIND_MEMBER_ID))
                .getMemberId();


        List<CategoryListDto> categoryListDtoList = categoryNeo4jRepository.findCategoryByCount(memberId);


        int totalSize = categoryListDtoList.stream()
                .mapToInt(CategoryListDto::getAnswerCount)
                .sum();
        categoryListDtoList.forEach(dto -> dto.setTotalCount(totalSize));

        return categoryListDtoList;
    }

    public List<QuestionDto> getCategoryToQuestion(Long loginMember, Long cid) {
        Long memberId = memberNeo4jRepository.findByMemberId(loginMember)
                .orElseThrow(() -> new GlobalException(ErrorCode.NOT_FIND_MEMBER_ID))
                .getMemberId();

        return questionNeo4jRepository.findCategoryIdToQuestion(memberId,cid);

    }

    @Transactional
    public void deleteToAnswerId(Long loginMember, Long answerId) {
        Long memberId = memberNeo4jRepository.findByMemberId(loginMember)
                .orElseThrow(() -> new GlobalException(ErrorCode.NOT_FIND_MEMBER_ID))
                .getMemberId();

        Answer answer = answerNeo4jRepository.findById(answerId)
                .orElseThrow(()->new GlobalException(ErrorCode.NOT_FIND_ANSWER_ID));

        answerNeo4jRepository.delete(answer);
    }
}
