package com.example.tooktook.controller;

import com.example.tooktook.model.dto.CategoryDto;
import com.example.tooktook.model.entity.Member;
import com.example.tooktook.service.Neo4jService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ques")
@RequiredArgsConstructor
public class QuestionController {

    private final Neo4jService neo4jService;

    @PostMapping("/default/{loginMember}")
    public ResponseEntity<Member> getMemberId(
              @PathVariable("loginMember") Long loginMember) {
        return ResponseEntity.ok(neo4jService.createMemberWithDefault(loginMember));
    }

    @PostMapping("/{questionId}/answers")
    public ResponseEntity<String> addAnswerToQuestion(@PathVariable Long questionId, @RequestBody String answerText) {

        return ResponseEntity.ok(neo4jService.addAnswerToQuestion(questionId,answerText));
    }
    @GetMapping("/find/{loginMember}")
    public ResponseEntity<List<CategoryDto>> getMemberIdToAllQuestionId(@PathVariable("loginMember") Long loginMember){
        return ResponseEntity.ok(neo4jService.findMemberIdToQuestionId(loginMember));
    }
}
