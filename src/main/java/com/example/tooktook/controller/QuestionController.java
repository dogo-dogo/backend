package com.example.tooktook.controller;

import com.example.tooktook.model.dto.CategoryDto;
import com.example.tooktook.model.dto.CategoryListDto;
import com.example.tooktook.model.dto.QuestionDto;
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
    public void addAnswerToQuestion(@PathVariable Long questionId, @RequestBody String answerText) {
        neo4jService.addAnswerToQuestion(questionId,answerText);
    }
    @GetMapping("/find/category/{loginMember}")
    public ResponseEntity<List<CategoryListDto>> getMemberIdToCategoryAllCount(@PathVariable("loginMember") Long loginMember){
        return ResponseEntity.ok(neo4jService.getAllCategoryCount(loginMember));
    }
    @GetMapping("/find/question/{loginMember}")
    public ResponseEntity<List<QuestionDto>> getCategoryToQuestion(@PathVariable("loginMember") Long loginMember, @RequestParam Long cid){
        return ResponseEntity.ok(neo4jService.getCategoryToQuestion(loginMember,cid));
    }
}
