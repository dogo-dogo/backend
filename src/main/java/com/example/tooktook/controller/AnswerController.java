package com.example.tooktook.controller;

import com.example.tooktook.common.response.ApiResponse;
import com.example.tooktook.common.response.ResponseCode;
import com.example.tooktook.common.response.ValidMember;
import com.example.tooktook.model.dto.answerDto.AnswerDAO;
import com.example.tooktook.model.dto.answerDto.AnswerPageDto;
import com.example.tooktook.model.dto.memberDto.MemberDetailsDto;
import com.example.tooktook.service.AnswerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Slf4j
public class AnswerController {
    private final AnswerService answerService;
    @GetMapping("/answers")
    public AnswerPageDto getAnswersByCategory(
            @AuthenticationPrincipal MemberDetailsDto memberEmail,
            @RequestParam(value = "sort",defaultValue = "createdAt") String sort,
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {

        ValidMember.validCheckNull(memberEmail);
        log.info("------------answerController 시작 ----------------");
        log.info("--------------path : /api/answers/ ---------------");
        log.info("-------------------requestParm sort: {} ", sort);
        log.info("-------------------requestParm page: {} ", page);
        log.info("-------------------requestParm size: {} ", size);
        Pageable pageable = PageRequest.of(page,size, Sort.by(sort).ascending());


        return answerService.getAnswersByCategory(pageable,memberEmail.getId());
    }
    @GetMapping("/answers/notify")
    public ApiResponse<Integer> getNotificationByAnswer(@AuthenticationPrincipal MemberDetailsDto memberEmail){
        ValidMember.validCheckNull(memberEmail);
        log.info("------------answerController 시작 ----------------");
        log.info("--------------path : /api/answers/notify ---------------");

        return ApiResponse.ok(ResponseCode.Normal.RETRIEVE,answerService.getNotification(memberEmail.getId()));
    }
    @GetMapping("/answers/details")
    public ApiResponse<AnswerDAO> getAnswerDetails(@AuthenticationPrincipal MemberDetailsDto member,
                                                   @RequestParam Long answerId){
        ValidMember.validCheckNull(member);
        log.info("------------answerController 시작 ----------------");
        log.info("--------------path : /api/answers/details ---------------");
        log.info("-------------------requestParm : {} ", answerId);


        return ApiResponse.ok(ResponseCode.Normal.RETRIEVE,answerService.getAnswerDetails(member.getId(),answerId));
    }
    @GetMapping("/answer/green")
    public ApiResponse<List<Boolean>> getAllcheckAnswerGreen(@AuthenticationPrincipal MemberDetailsDto member){
        return ApiResponse.ok(ResponseCode.Normal.RETRIEVE,answerService.getAllcheckAnswerGreen(member.getId()));

    }
}