package com.example.tooktook.service;

import com.example.tooktook.component.security.CurrentMember;
import com.example.tooktook.exception.ErrorCode;
import com.example.tooktook.exception.GlobalException;
import com.example.tooktook.model.dto.QuestionDTO;
import com.example.tooktook.model.entity.Category;
import com.example.tooktook.model.entity.Answer;
import com.example.tooktook.model.entity.Member;
import com.example.tooktook.model.entity.Question;
import com.example.tooktook.model.enumDto.*;
import com.example.tooktook.model.repository.AnswerNeo4jRepository;
import com.example.tooktook.model.repository.MemberNeo4jRepository;
import com.example.tooktook.model.repository.QuestionNeo4jRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class Neo4jService {
    private final MemberNeo4jRepository memberNeo4jRepository;

    private final QuestionNeo4jRepository questionNeo4jRepository;

    private final AnswerNeo4jRepository answerNeo4jRepository;
    public Member createMemberWithDefault(Long memberId) {

        Member member = memberNeo4jRepository.findByMemberId(memberId)
                .orElseThrow( () -> new GlobalException(ErrorCode.NOT_FIND_MEMBER_ID));

        String memberNickName = member.getNickname();

        if (!member.getVisit()) {

            for (CategoryEnum categoryEnum : CategoryEnum.values()) {
                Category category = new Category(categoryEnum.getText());
                member.addCategory(category);

                switch (categoryEnum.getText()){
                    case "BYE2023":
                        Arrays.stream(Bye2023.values())
                                .map(bye2023Enum -> new Question(String.format(bye2023Enum.getText(), memberNickName)))
                                .forEach(category::askQuestion);
                        break;
                    case "COMPLIMENT":
                        Arrays.stream(Compliment.values())
                                .map(complimentEnum -> new Question(String.format(complimentEnum.getText(), memberNickName)))
                                .forEach(category::askQuestion);
                        break;
                    case "FI":
                        Arrays.stream(Fi.values())
                                .map(fiEnum -> new Question(String.format(fiEnum.getText(),memberNickName,memberNickName)))
                                .forEach(category::askQuestion);
                        break;
                    case "GROWTH":
                        Arrays.stream(Growth.values())
                                .map(growthEnum -> new Question(String.format(growthEnum.getText(),memberNickName)))
                                .forEach(category::askQuestion);
                        break;
                    case "HELLO2024":
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

    public String addAnswerToQuestion(Long questionId, String answerText) {
        Optional<Question> questionOptional = questionNeo4jRepository.findById(questionId);
        if (questionOptional.isPresent()) {
            Question question = questionOptional.get();
            Answer answer = new Answer();
            answer.setText(answerText);

            // 질문과 답변을 연결
            question.askAnswer(answer);

            questionNeo4jRepository.save(question);
            answerNeo4jRepository.save(answer);

            return "답변이 추가되었습니다.";
        } else {
            throw new GlobalException(ErrorCode.WRONG_AUTHORIZATION_HEADER);
        }
    }

    public List<QuestionDTO> findMemberIdToQuestionId(CurrentMember loginMember) {
        return memberNeo4jRepository.findQuestionsByMemberId(loginMember.getMemberId());
    }

}
