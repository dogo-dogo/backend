package com.example.tooktook.controller;

import com.example.tooktook.common.response.ApiResponse;
import com.example.tooktook.common.response.ResponseCode;
import com.example.tooktook.component.jwt.JwtTokenProvider;
import com.example.tooktook.component.security.AuthTokens;
import com.example.tooktook.component.security.AuthTokensGenerator;
import com.example.tooktook.model.dto.MemberDto;
import com.example.tooktook.oauth.kakao.KakaoLoginParams;
import com.example.tooktook.service.KakaoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;


@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class AuthController {

    private final KakaoService kakaoService;
    @PostMapping("/auth/kakao")
    public ApiResponse<AuthTokens> loginKakao(@RequestBody KakaoLoginParams kakaoAccessCode, HttpServletResponse response) {

        AuthTokens authTokens = kakaoService.login(kakaoAccessCode,response);
        return ApiResponse.ok(ResponseCode.Normal.CREATE,authTokens);
    }

    @ResponseBody
    @GetMapping("/kakao/callback")
    public ResponseEntity<String> kakaoCallback(@RequestParam String code) {

        return ResponseEntity.ok(code);
    }

}
