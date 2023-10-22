package com.example.tooktook.controller;

import com.example.tooktook.common.response.ApiResponse;
import com.example.tooktook.common.response.ResponseCode;
import com.example.tooktook.model.dto.AnswerDto;
import com.example.tooktook.model.dto.CategoryDto;
import com.example.tooktook.model.dto.CategoryListDto;
import com.example.tooktook.model.dto.QuestionDto;
import com.example.tooktook.model.entity.Member;
import com.example.tooktook.service.Neo4jService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/ques")
@RequiredArgsConstructor
public class QuestionController {

    private final Neo4jService neo4jService;

    @PostMapping("/default")
    public ApiResponse<Member> getMemberId(
            @AuthenticationPrincipal UserDetails loginMember) {

        return ApiResponse.ok(ResponseCode.Normal.CREATE,
                neo4jService.createMemberWithDefault(loginMember.getUsername()));
    }

    @PostMapping("/{questionId}/answers")
    public ApiResponse<?> addAnswerToQuestion(@PathVariable Long questionId, @RequestBody @Valid AnswerDto answerdto) {
        neo4jService.addAnswerToQuestion(questionId,answerdto);
        return ApiResponse.ok(ResponseCode.Normal.CREATE,
                String.format("추가한 questionId > {%d} ", questionId));

    }
    @GetMapping("/find/category")
    public ApiResponse<List<CategoryListDto>> getMemberIdToCategoryAllCount(@AuthenticationPrincipal UserDetails loginMember){
        return ApiResponse.ok(ResponseCode.Normal.RETRIEVE,
                neo4jService.getAllCategoryCount(loginMember.getUsername()));
    }
    @GetMapping("/find/question")
    public ApiResponse<List<QuestionDto>> getCategoryToQuestion(@AuthenticationPrincipal UserDetails loginMember, @RequestParam Long cid){
        return ApiResponse.ok(ResponseCode.Normal.RETRIEVE,
                neo4jService.getCategoryToQuestion(loginMember.getUsername(),cid));
    }
    @DeleteMapping("/delete/answer")
    public ApiResponse<?> deleteToAnswerId(@AuthenticationPrincipal UserDetails loginMember, @RequestParam Long answerId){
        neo4jService.deleteToAnswerId(loginMember.getUsername(),answerId);
        return ApiResponse.ok(ResponseCode.Normal.DELETE,String.format("{%d} 이 삭제 됨",answerId));
    }
}
