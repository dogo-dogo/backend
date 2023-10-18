package com.example.tooktook.controller;

import com.example.tooktook.component.security.CurrentMember;
import com.example.tooktook.component.security.LoginMember;
import com.example.tooktook.model.dto.Neo4Dto;
import com.example.tooktook.model.dto.QuestionDTO;
import com.example.tooktook.model.entity.Member;
import com.example.tooktook.model.entity.Question;
import com.example.tooktook.service.Neo4jService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/ques")
@RequiredArgsConstructor
public class QuestionController {

    private final Neo4jService neo4jService;

    @PostMapping("/default")
    public ResponseEntity<Member> getMemberId(
             Long loginMember) {
        return ResponseEntity.ok(neo4jService.createMemberWithDefault(loginMember));
    }

    @PostMapping("/{questionId}/answers")
    public ResponseEntity<String> addAnswerToQuestion(@PathVariable Long questionId, @RequestBody String answerText) {

        return ResponseEntity.ok(neo4jService.addAnswerToQuestion(questionId,answerText));
    }
    @GetMapping("/find")
    public ResponseEntity<List<QuestionDTO>> getMemberIdToAllQuestionId(@LoginMember CurrentMember loginMember){
        return ResponseEntity.ok(neo4jService.findMemberIdToQuestionId(loginMember));
    }
}
