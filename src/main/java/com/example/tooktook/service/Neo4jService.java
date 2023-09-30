package com.example.tooktook.service;

import com.example.tooktook.component.security.CurrentMember;
import com.example.tooktook.exception.ErrorCode;
import com.example.tooktook.exception.GlobalException;
import com.example.tooktook.model.dto.Neo4Dto;
import com.example.tooktook.model.dto.QuestionDTO;
import com.example.tooktook.model.dto.QuestionEnum;
import com.example.tooktook.model.entity.Answer;
import com.example.tooktook.model.entity.Member;
import com.example.tooktook.model.entity.Question;
import com.example.tooktook.model.repository.AnswerNeo4jRepository;
import com.example.tooktook.model.repository.MemberNeo4jRepository;
import com.example.tooktook.model.repository.QuestionNeo4jRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
public class Neo4jService {
    private final MemberNeo4jRepository memberNeo4jRepository;

    private final QuestionNeo4jRepository questionNeo4jRepository;

    private final AnswerNeo4jRepository answerNeo4jRepository;
    public Member createMemberWithDefault(Long memberId ,Neo4Dto neo4Dto) {
        Member member = memberNeo4jRepository.findByMemberId(memberId)
                .orElse(null);
        if(member == null) { // 회원이 존재 하지 않는다면 새로운 회원의 정보를 생성

            List<Question> questions = Arrays.stream(QuestionEnum.values())
                    .map(questionEnum -> {
                        Question question = new Question();
                        question.setText(questionEnum.getText());
                        return question;
                    })
                    .peek(questionNeo4jRepository::save)
                    .collect(Collectors.toList());

            member.setColor(neo4Dto.getColor());
            member.setSize(neo4Dto.getSize());
            member.setNickname(neo4Dto.getNickName());


            for (Question question : questions) {
                member.askQuestion(question);
            }

            memberNeo4jRepository.save(member);
        }
        else{
            member.setColor(neo4Dto.getColor());
            member.setSize(neo4Dto.getSize());
            member.setNickname(neo4Dto.getNickName());
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
