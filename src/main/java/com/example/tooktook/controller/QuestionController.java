package com.example.tooktook.controller;

import com.example.tooktook.common.response.ApiResponse;
import com.example.tooktook.common.response.ResponseCode;
import com.example.tooktook.common.response.ValidMember;
import com.example.tooktook.model.dto.answerDto.AnswerDto;
import com.example.tooktook.model.dto.answerDto.RandomAnswerDto;
import com.example.tooktook.model.dto.categoryDto.CategoryCountDto;
import com.example.tooktook.model.dto.categoryDto.CategoryListDto;
import com.example.tooktook.model.dto.categoryDto.mainPageDto;
import com.example.tooktook.model.dto.memberDto.MemberDetailsDto;
import com.example.tooktook.model.dto.questionDto.QuestionOtherDto;
import com.example.tooktook.model.dto.questionDto.QuestionRndDto;
import com.example.tooktook.model.entity.Member;
import com.example.tooktook.model.entity.Question;
import com.example.tooktook.service.Neo4jService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
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

    @GetMapping("/myspace")
    public ApiResponse<List<mainPageDto>> mySpaceGetAll(@AuthenticationPrincipal MemberDetailsDto loginMember){
        return ApiResponse.ok(ResponseCode.Normal.RETRIEVE,neo4jService.findAllListMain(loginMember.getId()));
    }

    @PostMapping("/answers/{questionId}/{memberId}")
    public ApiResponse<?> addAnswerToQuestion(@PathVariable Long questionId, @RequestBody @Valid AnswerDto answerdto,@PathVariable Long memberId) {

        log.info("------------QuestionController 시작 ----------------");
        log.info("--------------path : /api/ques/{questionId}/answers ---------------");
        log.info("--------------PathVariable : {} ---------------" ,questionId);

        Long answerId = neo4jService.addAnswerToQuestion(questionId,answerdto,memberId);


        log.info("------------QuestionController 종료 ----------------");
        return ApiResponse.ok(ResponseCode.Normal.CREATE, answerId);

    }
    @GetMapping("/find/category")
    public ApiResponse<CategoryCountDto> getMemberIdToCategoryAllCount(@AuthenticationPrincipal MemberDetailsDto loginMember){
        log.info("------------QuestionController 시작 ----------------");
        log.info("--------------path : /api/ques/find/category ---------------");

        ValidMember.validCheckNull(loginMember);
        return ApiResponse.ok(ResponseCode.Normal.RETRIEVE,
                neo4jService.getAllCategoryCount(loginMember.getId()));
    }

    @GetMapping("/find/question")
    public ApiResponse<?> getCategoryToQuestion(@AuthenticationPrincipal MemberDetailsDto loginMember, @RequestParam Long cid){
        log.info("------------QuestionController 시작 ----------------");
        log.info("--------------path : /api/ques/find/question ---------------");
        log.info("-------------------requestParm : {} ", cid);
        ValidMember.validCheckNull(loginMember);
        if(cid == 99999){
            return ApiResponse.ok(ResponseCode.Normal.RETRIEVE,
                    neo4jService.getAllCategoryToQuestions(loginMember.getId()));
        }else{
            return ApiResponse.ok(ResponseCode.Normal.RETRIEVE,
                    neo4jService.getCategoryToQuestion(loginMember.getId(),cid));
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
    @GetMapping("/rnd")
    public ApiResponse<RandomAnswerDto> randomCategoryAndQuestion(@AuthenticationPrincipal MemberDetailsDto loginMember){

        log.info("-----------------QuestionController 시작 ---------------");
        log.info("-------------path : /api/ques/rnd");
        ValidMember.validCheckNull(loginMember);

        RandomAnswerDto randomAnswerDto = neo4jService.randomReadCategoryAndQuestion(loginMember.getId());
        return ApiResponse.ok(ResponseCode.Normal.RETRIEVE,randomAnswerDto);
    }
    @GetMapping("/other/question")
    public ApiResponse<?> otherCategoryAndQuestion(@AuthenticationPrincipal MemberDetailsDto loginMember, @RequestParam Long rndCid){

        log.info("-----------------QuestionController 시작 ---------------");
        log.info("-------------path : /api/other/question");
        ValidMember.validCheckNull(loginMember);

        QuestionOtherDto questionLst = neo4jService.otherCategoryAndQuestion(loginMember.getId(), rndCid);

        return ApiResponse.ok(ResponseCode.Normal.RETRIEVE,questionLst);
    }


}
