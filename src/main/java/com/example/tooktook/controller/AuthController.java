package com.example.tooktook.controller;

import com.example.tooktook.common.response.ApiResponse;
import com.example.tooktook.common.response.ResponseCode;
import com.example.tooktook.component.security.AuthTokens;
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

        log.info("------------AuthController 시작 ----------------");
        log.info("--------------path : /api/auth/kakao ---------------");


        AuthTokens authTokens = kakaoService.login(kakaoAccessCode,response);

        log.info("------------AuthController  종료---------------");
        return ApiResponse.ok(ResponseCode.Normal.CREATE,authTokens);
    }

    @ResponseBody
    @GetMapping("/kakao/callback")
    public ResponseEntity<String> kakaoCallback(@RequestParam String code) {

        log.info("------------AuthController 시작 ----------------");
        log.info("--------------path : /api/kakao/callback ---------------");
        log.info("------------AuthController 종료---------------");

        return ResponseEntity.ok(code);
    }

}
