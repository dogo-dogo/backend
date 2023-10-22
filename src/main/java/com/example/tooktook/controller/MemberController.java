package com.example.tooktook.controller;

import com.example.tooktook.common.response.ApiResponse;
import com.example.tooktook.common.response.ResponseCode;
import com.example.tooktook.model.repository.MemberNeo4jRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/member")
@Slf4j
public class MemberController {

    private final MemberNeo4jRepository memberNeo4jRepository;
    @GetMapping("/checkTest")
    public ApiResponse<?> checkTest(@AuthenticationPrincipal UserDetails userDetails){
        log.info("MEMBER EMAIL : {}", userDetails.getUsername());
        // memberId = 5
        log.info("MEMBER ID {}", memberNeo4jRepository.findByLoginEmail(userDetails.getUsername()).get().getMemberId());
        return ApiResponse.ok(ResponseCode.Normal.SUCCESS,userDetails.getUsername());
    }

}
