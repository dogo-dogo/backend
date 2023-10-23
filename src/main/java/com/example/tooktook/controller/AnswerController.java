package com.example.tooktook.controller;

import com.example.tooktook.common.response.ValidMember;
import com.example.tooktook.model.dto.answerDto.AnswerPageDto;
import com.example.tooktook.model.dto.memberDto.MemberDetailsDto;
import com.example.tooktook.model.entity.Answer;
import com.example.tooktook.model.repository.AnswerNeo4jRepository;
import com.example.tooktook.service.AnswerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

        Pageable pageable = PageRequest.of(page,size, Sort.by(sort).ascending());

        return answerService.getAnswersByCategory(pageable,memberEmail);
    }

}