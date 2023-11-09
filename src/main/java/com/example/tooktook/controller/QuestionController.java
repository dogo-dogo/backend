package com.example.tooktook.controller;

import com.example.tooktook.common.response.ApiResponse;
import com.example.tooktook.common.response.ResponseCode;
import com.example.tooktook.common.response.ValidMember;
import com.example.tooktook.model.dto.answerDto.AnswerDto;
import com.example.tooktook.model.dto.categoryDto.CategoryListDto;
import com.example.tooktook.model.dto.memberDto.MemberDetailsDto;
import com.example.tooktook.model.entity.Member;
import com.example.tooktook.model.entity.Question;
import com.example.tooktook.service.Neo4jService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/ques")
@RequiredArgsConstructor
@Slf4j
public class QuestionController {

    private final Neo4jService neo4jService;

    @PostMapping("/default")
    public ApiResponse<Member> getMemberId(
            @AuthenticationPrincipal MemberDetailsDto loginMember) {
        ValidMember.validCheckNull(loginMember);

        log.info("------------QuestionController 시작 ----------------");
        log.info("--------------path : /api/ques/default ---------------");


        return ApiResponse.ok(ResponseCode.Normal.CREATE,
                neo4jService.createMemberWithDefault(loginMember.getUsername()));
    }

    @PostMapping("/{questionId}/answers")
    public ApiResponse<?> addAnswerToQuestion(@PathVariable Long questionId, @RequestBody @Valid AnswerDto answerdto) {

        log.info("------------QuestionController 시작 ----------------");
        log.info("--------------path : /api/ques/{questionId}/answers ---------------");
        log.info("--------------PathVariable : {} ---------------" ,questionId);

        Long answerId = neo4jService.addAnswerToQuestion(questionId,answerdto);


        log.info("------------QuestionController 종료 ----------------");
        return ApiResponse.ok(ResponseCode.Normal.CREATE, answerId);

    }
    @GetMapping("/find/category")
    public ApiResponse<List<CategoryListDto>> getMemberIdToCategoryAllCount(@AuthenticationPrincipal MemberDetailsDto loginMember){
        log.info("------------QuestionController 시작 ----------------");
        log.info("--------------path : /api/ques/find/category ---------------");

        ValidMember.validCheckNull(loginMember);
        return ApiResponse.ok(ResponseCode.Normal.RETRIEVE,
                neo4jService.getAllCategoryCount(loginMember.getUsername()));
    }

    @GetMapping("/find/question")
    public ApiResponse<?> getCategoryToQuestion(@AuthenticationPrincipal MemberDetailsDto loginMember, @RequestParam Long cid){
        log.info("------------QuestionController 시작 ----------------");
        log.info("--------------path : /api/ques/find/question ---------------");
        log.info("-------------------requestParm : {} ", cid);
        ValidMember.validCheckNull(loginMember);
        if(cid == 99999){
            return ApiResponse.ok(ResponseCode.Normal.RETRIEVE,
                    neo4jService.getAllCategoryToQuestions(loginMember.getUsername()));
        }else{
            return ApiResponse.ok(ResponseCode.Normal.RETRIEVE,
                    neo4jService.getCategoryToQuestion(loginMember.getUsername(),cid));
        }
    }

    @DeleteMapping("/delete/answer")
    public ApiResponse<?> deleteToAnswerId(@AuthenticationPrincipal MemberDetailsDto loginMember, @RequestParam Long answerId){
        log.info("------------QuestionController 시작 ----------------");
        log.info("--------------path : /api/ques/delete/answer ---------------");
        log.info("-------------------requestParm : {} ", answerId);
        ValidMember.validCheckNull(loginMember);
        neo4jService.deleteToAnswerId(loginMember.getUsername(),answerId);

        log.info("------------QuestionController 종료 ----------------");
        return ApiResponse.ok(ResponseCode.Normal.DELETE,String.format("{%d} 이 삭제 됨",answerId));
    }
}
